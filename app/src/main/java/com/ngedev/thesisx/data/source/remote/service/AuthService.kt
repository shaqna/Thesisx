package com.ngedev.thesisx.data.source.remote.service

import com.ngedev.thesisx.domain.model.UserAccount
import com.ngedev.thesisx.data.source.remote.response.UserAccountResponse
import com.ngedev.thesisx.data.source.remote.network.FirebaseResponse
import com.ngedev.thesisx.utils.FirebaseConstant
import kotlinx.coroutines.flow.*

class AuthService : FirebaseService() {

    fun signUp(
        email: String,
        password: String,
        user: UserAccount
    ): Flow<FirebaseResponse<UserAccountResponse>> =
        flow {
            createUserWithEmailAndPassword(email, password).collect { response ->
                when (response) {
                    is FirebaseResponse.Success -> {
                        emitAll(
                            setDocument<UserAccount, UserAccountResponse>(
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

    fun signIn(email: String, password: String): Flow<FirebaseResponse<UserAccountResponse>> =
        flow {
            signInWithEmailAndPassword(email, password).collect { response ->
                when (response) {
                    is FirebaseResponse.Success -> {
                        emitAll(
                            getDocument<UserAccountResponse>(
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
}