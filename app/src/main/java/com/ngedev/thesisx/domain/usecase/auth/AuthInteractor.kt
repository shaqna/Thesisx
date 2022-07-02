package com.ngedev.thesisx.domain.usecase.auth

import com.ngedev.thesisx.domain.model.UserAccount
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.Flow

class AuthInteractor(private val authRepository: IAuthRepository) : AuthUseCase {
    override fun signIn(email: String, password: String): Flow<Resource<Unit>> =
        authRepository.signIn(email, password)

    override fun signUp(user: UserAccount, email: String, password: String): Flow<Resource<Unit>> =
        authRepository.signUp(user, email, password)


}