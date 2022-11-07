package com.ngedev.thesisx.data.source.remote.service

import com.ngedev.thesisx.domain.model.User
import com.ngedev.thesisx.data.source.remote.response.UserResponse
import com.ngedev.thesisx.data.source.remote.network.FirebaseResponse
import com.ngedev.thesisx.utils.FirebaseConstant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await

class AuthService : FirebaseService() {

    fun signUp(
        email: String,
        password: String,
        user: User
    ): Flow<FirebaseResponse<UserResponse>> =
        flow {
            createUserWithEmailAndPassword(email, password).collect { response ->
                when (response) {
                    is FirebaseResponse.Success -> {
                        emitAll(
                            setDocument<User, UserResponse>(
                                FirebaseConstant.Collections.USER_COLLECTION,
                                response.data,
                                user.copy(uid = response.data)
                            )
                        )
                    }
                    is FirebaseResponse.Error -> {
                        emit(FirebaseResponse.Error(response.errorMessage))
                    }
                    is FirebaseResponse.Empty -> {
                        emit(FirebaseResponse.Empty)
                    }
                }
            }
        }

    fun signIn(email: String, password: String): Flow<FirebaseResponse<UserResponse>> =
        flow {
            signInWithEmailAndPassword(email, password).collect { response ->
                when (response) {
                    is FirebaseResponse.Success -> {
                        emitAll(
                            getDocument<UserResponse>(
                                FirebaseConstant.Collections.USER_COLLECTION,
                                response.data
                            )
                        )
                    }

                    is FirebaseResponse.Error -> {
                        emit(FirebaseResponse.Error(response.errorMessage))
                    }

                    is FirebaseResponse.Empty -> {
                        emit(FirebaseResponse.Empty)
                    }
                }
            }
        }

    fun resetPassword(email: String): Flow<FirebaseResponse<Unit>> =
        flow {
            val resetPasswordResponse = auth.sendPasswordResetEmail(email).await()
            if(resetPasswordResponse != null) {
                emit(FirebaseResponse.Success(resetPasswordResponse as Unit))
            } else {
                emit(FirebaseResponse.Empty)
            }
        }.catch {
            emit(FirebaseResponse.Error(it.message.toString()))
        }.flowOn(Dispatchers.IO)
}