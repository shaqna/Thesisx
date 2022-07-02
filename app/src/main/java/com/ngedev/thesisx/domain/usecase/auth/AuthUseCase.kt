package com.ngedev.thesisx.domain.usecase.auth

import com.ngedev.thesisx.domain.model.UserAccount
import com.ngedev.thesisx.domain.Resource
import kotlinx.coroutines.flow.Flow

interface AuthUseCase {
    fun signIn(email: String, password: String): Flow<Resource<Unit>>
    fun signUp(user: UserAccount, email: String, password: String): Flow<Resource<Unit>>
}