package com.ngedev.thesisx.domain.usecase.settings

import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.User
import com.ngedev.thesisx.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow

class SettingsInteractor(private val repository: IUserRepository): SettingsUseCase {
    override fun getCurrentUser(): Flow<Resource<User>> {
        return repository.getCurrentUser()
    }

    override fun changeUsername(username: String): Flow<Resource<Unit>> {
        return repository.updateUsername(username)
    }

    override fun logOut() {
        repository.logOut()
    }
}