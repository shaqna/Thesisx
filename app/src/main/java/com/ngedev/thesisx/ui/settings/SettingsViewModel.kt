package com.ngedev.thesisx.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.UserAccount
import com.ngedev.thesisx.domain.usecase.settings.SettingsUseCase

class SettingsViewModel(private val useCase: SettingsUseCase) : ViewModel() {
    fun getUsernameAndEmail(): LiveData<Resource<UserAccount>> {
        return useCase.getCurrentUser().asLiveData()
    }

    fun signOut(): Unit = useCase.logOut()
}