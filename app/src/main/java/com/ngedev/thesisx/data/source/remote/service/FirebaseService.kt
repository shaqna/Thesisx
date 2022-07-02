package com.ngedev.thesisx.data.source.remote.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ngedev.thesisx.data.source.remote.network.FirebaseResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

abstract class FirebaseService {
    val auth = FirebaseAuth.getInstance()
    val fireStore = Firebase.firestore

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

    inline fun <RequestType, reified ResponseType> setDocument(
        collection: String,
        docId: String,
        document: RequestType
    ): Flow<FirebaseResponse<ResponseType>> =
        flow {
            fireStore.collection(collection).document(docId).set(document as Any).await()
            emitAll(getDocument<ResponseType>(collection, docId))
        }.catch {
            emit(FirebaseResponse.Error(it.message.toString()))
        }.flowOn(Dispatchers.IO)

    inline fun <reified ResponseType> getDocument(
        collection: String,
        docId: String
    ): Flow<FirebaseResponse<ResponseType>> =
        flow {
            val result = fireStore
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
            val resultCollection = fireStore.collection(collection).get().await()

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
            fireStore.collection(collection).document(docId)
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
            fireStore.collection(collection)
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
            fireStore
                .collection(collection)
                .document(docId)
                .update(fieldName, value)
                .await()

            emitAll(getDocument<ResponseType>(collection, docId))
        }.catch {
            emit(FirebaseResponse.Error(it.message.toString()))
        }.flowOn(Dispatchers.IO)

    fun <ValueType> modifyValueInField(
        collection: String,
        docId: String,
        fieldName: String,
        value: ValueType
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            fireStore.collection(collection).document(docId).update(fieldName, value).await()
        }
    }

    inline fun <reified ResponseType> getDocumentsWhereIds(
        collection: String,
        fieldName: String,
        ids: List<String>
    ): Flow<FirebaseResponse<List<ResponseType>>> =
        flow {
            val result = fireStore.collection(collection).whereIn(fieldName, ids).get().await()
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
        fieldName: String,
        query: String
    ): Flow<FirebaseResponse<List<ResponseType>>> =
        flow {
            val result = fireStore.collection(collection).orderBy(fieldName).startAt(query)
                .endAt(query + '\uf8ff')
                .get().await()

            if(!result.isEmpty) {
                emit(FirebaseResponse.Success(result.toObjects(ResponseType::class.java)))
            } else {
                emit(FirebaseResponse.Empty)
            }
        }.catch {
            emit(FirebaseResponse.Error(it.message.toString()))
        }.flowOn(Dispatchers.IO)

    fun signOut(): Unit = auth.signOut()
}