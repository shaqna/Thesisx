package com.ngedev.thesisx.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ngedev.thesisx.domain.usecase.auth.AuthUseCase

class LoginViewModel(private val authUseCase: AuthUseCase): ViewModel() {

    fun signIn(email: String, password: String) = authUseCase.signIn(email, password).asLiveData()

}