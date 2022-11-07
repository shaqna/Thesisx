package com.ngedev.thesisx.domain.repository

import com.ngedev.thesisx.domain.model.User
import com.ngedev.thesisx.domain.Resource
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun getCurrentUser(): Flow<Resource<User>>
    fun getCurrentUserId(): String
    fun getUser(id: String): Flow<Resource<User>>
    fun updateUsername(newUsername: String): Flow<Resource<Unit>>
    fun logOut(): Unit
}