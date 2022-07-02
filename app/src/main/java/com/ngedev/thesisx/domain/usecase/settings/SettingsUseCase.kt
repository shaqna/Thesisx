package com.ngedev.thesisx.domain.usecase.settings

import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.UserAccount
import kotlinx.coroutines.flow.Flow

interface SettingsUseCase {

    fun getCurrentUser(): Flow<Resource<UserAccount>>
    fun logOut(): Unit
}