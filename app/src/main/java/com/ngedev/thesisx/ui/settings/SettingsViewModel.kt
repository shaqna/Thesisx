package com.ngedev.thesisx.ui.settings

import androidx.lifecycle.*
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.User
import com.ngedev.thesisx.domain.usecase.settings.SettingsUseCase
import kotlinx.coroutines.launch

class SettingsViewModel(private val useCase: SettingsUseCase) : ViewModel() {

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    fun setUsername(username: String) {
        viewModelScope.launch {
            _username.value = username
        }
    }

    fun setEmail(email: String) {
        viewModelScope.launch {
            _email.value = email
        }
    }

    fun getUsernameAndEmail(): LiveData<Resource<User>> {
        return useCase.getCurrentUser().asLiveData()
    }

    fun changeUsername(username: String): LiveData<Resource<Unit>> =
        useCase.changeUsername(username).asLiveData()

    fun signOut(): Unit = useCase.logOut()
}