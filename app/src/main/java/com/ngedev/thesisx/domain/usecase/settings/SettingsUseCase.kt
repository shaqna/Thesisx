package com.ngedev.thesisx.domain.usecase.settings

import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.User
import kotlinx.coroutines.flow.Flow

interface SettingsUseCase {

    fun getCurrentUser(): Flow<Resource<User>>
    fun changeUsername(username: String): Flow<Resource<Unit>>
    fun logOut(): Unit
}