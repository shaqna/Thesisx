package com.ngedev.thesisx.ui.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ngedev.thesisx.domain.model.User
import com.ngedev.thesisx.domain.usecase.auth.AuthUseCase

class RegisterViewModel(private val authUseCase: AuthUseCase) : ViewModel() {
    fun signUp(email: String, password: String, user: User) =
        authUseCase.signUp(user, email, password).asLiveData()
}