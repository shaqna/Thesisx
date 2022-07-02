package com.ngedev.thesisx.domain.usecase.settings

import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.UserAccount
import com.ngedev.thesisx.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow

class SettingsInteractor(private val repository: IUserRepository): SettingsUseCase {
    override fun getCurrentUser(): Flow<Resource<UserAccount>> {
        return repository.getCurrentUser()
    }

    override fun logOut() {
        repository.logOut()
    }
}