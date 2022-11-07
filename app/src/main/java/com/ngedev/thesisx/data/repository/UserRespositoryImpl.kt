package com.ngedev.thesisx.data.repository

import com.ngedev.thesisx.data.cache.NetworkBoundRequest
import com.ngedev.thesisx.data.cache.NetworkBoundResource
import com.ngedev.thesisx.data.source.local.LocalDataSource
import com.ngedev.thesisx.data.mapper.toEntity
import com.ngedev.thesisx.data.mapper.toFlowModel
import com.ngedev.thesisx.domain.model.User
import com.ngedev.thesisx.data.source.remote.RemoteDataSource
import com.ngedev.thesisx.data.source.remote.network.FirebaseResponse
import com.ngedev.thesisx.data.source.remote.response.UserResponse
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class UserRespositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : IUserRepository {
    override fun getCurrentUser(): Flow<Resource<User>> = flow {
        val userId = getCurrentUserId()
        if (userId.isNotEmpty()) {
            emitAll(getUser(userId))
        }
    }

    override fun getCurrentUserId(): String =
        remoteDataSource.getCurrentUserId()


    override fun getUser(id: String): Flow<Resource<User>> =
        object : NetworkBoundResource<User, UserResponse>() {
            override fun loadFromDB(): Flow<User?> {
                return localDataSource.selectUser().toFlowModel()
            }

            override fun shouldFetch(data: User?): Boolean {
                return data == null
            }

            override suspend fun createCall(): Flow<FirebaseResponse<UserResponse>> {
                return remoteDataSource.getCurrentUser(id)
            }

            override suspend fun saveCallResult(data: UserResponse) {
                return localDataSource.insertUser(data.toEntity())
            }

        }.asFlow()

    override fun updateUsername(newUsername: String): Flow<Resource<Unit>> =
        object : NetworkBoundRequest<UserResponse>() {
            override suspend fun createCall(): Flow<FirebaseResponse<UserResponse>> {
                return remoteDataSource.updateUsername(newUsername)
            }

            override suspend fun saveCallResult(data: UserResponse) {
                localDataSource.insertUser(data.toEntity())
            }

        }.asFlow()

    override fun logOut() {
        remoteDataSource.logOut()
    }
}