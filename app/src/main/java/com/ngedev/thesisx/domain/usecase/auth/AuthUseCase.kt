package com.ngedev.thesisx.domain.usecase.auth

import com.ngedev.thesisx.domain.model.User
import com.ngedev.thesisx.domain.Resource
import kotlinx.coroutines.flow.Flow

interface AuthUseCase {
    fun signIn(email: String, password: String): Flow<Resource<Unit>>
    fun signUp(user: User, email: String, password: String): Flow<Resource<Unit>>
    fun resetPassword(email: String): Flow<Resource<Unit>>
}