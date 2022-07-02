package com.ngedev.thesisx.domain.repository

import com.ngedev.thesisx.domain.model.UserAccount
import com.ngedev.thesisx.domain.Resource
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun getCurrentUser(): Flow<Resource<UserAccount>>
    fun getCurrentUserId(): String
    fun getUser(id: String): Flow<Resource<UserAccount>>
    fun updateUsername(newUsername: String): Flow<Resource<Unit>>
    fun logOut(): Unit
}