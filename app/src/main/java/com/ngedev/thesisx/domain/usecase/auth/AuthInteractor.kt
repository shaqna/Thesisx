package com.ngedev.thesisx.domain.usecase.auth

import com.ngedev.thesisx.domain.model.User
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.Flow

class AuthInteractor(private val authRepository: IAuthRepository) : AuthUseCase {
    override fun signIn(email: String, password: String): Flow<Resource<Unit>> =
        authRepository.signIn(email, password)

    override fun signUp(user: User, email: String, password: String): Flow<Resource<Unit>> =
        authRepository.signUp(user, email, password)

    override fun resetPassword(email: String): Flow<Resource<Unit>> {
        return authRepository.resetPassword(email)
    }


}