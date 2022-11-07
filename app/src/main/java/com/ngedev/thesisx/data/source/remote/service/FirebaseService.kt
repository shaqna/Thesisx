package com.ngedev.thesisx.data.source.remote.service

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.ngedev.thesisx.data.source.remote.network.FirebaseResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

abstract class FirebaseService {
    val auth = FirebaseAuth.getInstance()
    val firestore = Firebase.firestore
    val storage = Firebase.storage

    fun generateDocumentId(collection: String): String =
        firestore.collection(collection).document().id

    fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<FirebaseResponse<String>> = flow {
        val createUserSignUp = auth.createUserWithEmailAndPassword(email, password).await()
        val user = createUserSignUp.user
        user?.let {
            emit(FirebaseResponse.Success(it.uid))
        } ?: emit(FirebaseResponse.Empty)
    }.catch {
        emit(FirebaseResponse.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)


    fun uploadPicture(
        reference: String,
        fileName: String,
        pictureURI: Uri
    ): Flow<FirebaseResponse<String>> =
        flow {
            try {
                val filePath = storage.reference.child("$reference/$fileName.jpg")

                filePath.putFile(Uri.parse(pictureURI.toString())).await()
                val pictureUrl = filePath.downloadUrl.await()

                emit(FirebaseResponse.Success(pictureUrl.toString()))
            } catch (e: Exception) {
                emit(FirebaseResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    inline fun <RequestType, reified ResponseType> setDocument(
        collection: String,
        docId: String,
        document: RequestType
    ): Flow<FirebaseResponse<ResponseType>> =
        flow {
            firestore.collection(collection).document(docId).set(document as Any).await()
            emitAll(getDocument<ResponseType>(collection, docId))
        }.catch {
            emit(FirebaseResponse.Error(it.message.toString()))
        }.flowOn(Dispatchers.IO)


    inline fun <reified ResponseType> getDocument(
        collection: String,
        docId: String
    ): Flow<FirebaseResponse<ResponseType>> =
        flow {
            val result = firestore
                .collection(collection)
                .document(docId)
                .get()
                .await()

            if (result.exists()) {
                emit(FirebaseResponse.Success(result.toObject(ResponseType::class.java)!!))
            } else {
                emit(FirebaseResponse.Empty)
            }
        }.catch {
            emit(FirebaseResponse.Error(it.message.toString()))
        }.flowOn(Dispatchers.IO)

    fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<FirebaseResponse<String>> =
        flow {
            val createUserSignIn = auth.signInWithEmailAndPassword(email, password).await()
            val user = createUserSignIn.user
            user?.let {
                emit(FirebaseResponse.Success(user.uid))
            } ?: emit(FirebaseResponse.Empty)
        }.catch {
            emit(FirebaseResponse.Error(it.message.toString()))
        }.flowOn(Dispatchers.IO)

    inline fun <reified ResponseType> getCollection(collection: String): Flow<FirebaseResponse<List<ResponseType>>> =
        flow {
            val resultCollection = firestore.collection(collection).get().await()
            Log.d("LogLogLog", resultCollection.toString())

            if (!resultCollection.isEmpty) {
                emit(FirebaseResponse.Success(resultCollection.toObjects(ResponseType::class.java)))
            } else {
                emit(FirebaseResponse.Empty)
            }
        }.catch {
            emit(FirebaseResponse.Error(it.message.toString()))
        }.flowOn(Dispatchers.IO)

    fun addStringToArrayValueAtDocField(
        collection: String,
        docId: String,
        fieldName: String,
        value: String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            firestore.collection(collection).document(docId)
                .update(fieldName, FieldValue.arrayUnion(value)).await()
        }
    }

    fun removeStringValueInArrayAtDocField(
        collection: String,
        docId: String,
        fieldName: String,
        value: String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            firestore.collection(collection)
                .document(docId)
                .update(fieldName, FieldValue.arrayRemove(value))
                .await()
        }
    }

    inline fun <RequestType, reified ResponseType> updateFieldInDocument(
        collection: String,
        docId: String,
        fieldName: String,
        value: String
    ): Flow<FirebaseResponse<ResponseType>> =
        flow {
            firestore
                .collection(collection)
                .document(docId)
                .update(fieldName, value)
                .await()

            emitAll(getDocument<ResponseType>(collection, docId))
        }.catch {
            emit(FirebaseResponse.Error(it.message.toString()))
        }.flowOn(Dispatchers.IO)

    fun updateThesisAvailability(
        collection: String,
        docId: String,
        fieldName: String,
        value: Boolean
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            firestore.collection(collection).document(docId).update(fieldName, value).await()
        }
    }

    inline fun <reified ResponseType> getDocumentsWhereIds(
        collection: String,
        fieldName: String,
        ids: List<String>
    ): Flow<FirebaseResponse<List<ResponseType>>> =
        flow {
            val result = firestore.collection(collection).whereIn(fieldName, ids).get().await()
            if (!result.isEmpty) {
                emit(FirebaseResponse.Success(result.toObjects(ResponseType::class.java)))
            } else {
                emit(FirebaseResponse.Empty)
            }
        }.catch {
            emit(FirebaseResponse.Error(it.message.toString()))
        }.flowOn(Dispatchers.IO)

    inline fun <reified ResponseType> searchInCollection(
        collection: String,
        fieldName: List<String>,
        query: String
    ): Flow<FirebaseResponse<List<ResponseType>>> =
        flow {
            val result = firestore.collection(collection)
                .orderBy(fieldName[0])
                .endAt(query + '\uf8ff')
                .get().await()

            if (!result.isEmpty) {
                emit(FirebaseResponse.Success(
                    result.toObjects(ResponseType::class.java))
                )
            } else {
                emit(FirebaseResponse.Empty)
            }
        }.catch {
            emit(FirebaseResponse.Error(it.message.toString()))
        }.flowOn(Dispatchers.IO)

    fun deleteDocument(collection: String, docId: String): Flow<FirebaseResponse<Unit>> =
        flow<FirebaseResponse<Unit>> {
            firestore
                .collection(collection)
                .document(docId)
                .delete()
                .await()

            emit(FirebaseResponse.Success(Unit))

        }.catch {
            emit(FirebaseResponse.Error(it.message.toString()))
        }.flowOn(Dispatchers.IO)

    fun deleteFile(reference: String, fileName: String): Flow<FirebaseResponse<Unit>> =
        flow<FirebaseResponse<Unit>> {
            storage.reference
                .child(reference)
                .child("$fileName.jpg")
                .delete()
                .await()

            emit(FirebaseResponse.Success(Unit))
        }.catch {
            emit(FirebaseResponse.Error(it.message.toString()))
        }.flowOn(Dispatchers.IO)

    fun signOut(): Unit = auth.signOut()
}