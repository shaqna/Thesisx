package com.ngedev.thesisx.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ngedev.thesisx.domain.usecase.splash.SplashUseCase
import kotlinx.coroutines.launch

class SplashViewModel(private val useCase: SplashUseCase): ViewModel() {

    fun clearLoanWhenIsDeleted() {
        viewModelScope.launch {
            useCase.clearLoanWhenIsDeleted()
        }
    }
}