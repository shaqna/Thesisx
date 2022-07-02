package com.ngedev.thesisx.data.repository

import com.ngedev.thesisx.data.cache.NetworkBoundRequest
import com.ngedev.thesisx.data.cache.NetworkBoundResource
import com.ngedev.thesisx.data.source.local.LocalDataSource
import com.ngedev.thesisx.data.mapper.toEntity
import com.ngedev.thesisx.data.mapper.toFlowModel
import com.ngedev.thesisx.domain.model.UserAccount
import com.ngedev.thesisx.data.source.remote.RemoteDataSource
import com.ngedev.thesisx.data.source.remote.network.FirebaseResponse
import com.ngedev.thesisx.data.source.remote.response.UserAccountResponse
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class UserRespositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : IUserRepository {
    override fun getCurrentUser(): Flow<Resource<UserAccount>> = flow {
        val userId = getCurrentUserId()
        if (userId.isNotEmpty()) {
            emitAll(getUser(userId))
        }
    }

    override fun getCurrentUserId(): String {
        return remoteDataSource.getCurrentUserId()
    }

    override fun getUser(id: String): Flow<Resource<UserAccount>> =
        object : NetworkBoundResource<UserAccount, UserAccountResponse>() {
            override fun loadFromDB(): Flow<UserAccount?> {
                return localDataSource.selectUser().toFlowModel()
            }

            override fun shouldFetch(data: UserAccount?): Boolean {
                return data == null
            }

            override suspend fun createCall(): Flow<FirebaseResponse<UserAccountResponse>> {
                return remoteDataSource.getCurrentUser(id)
            }

            override suspend fun saveCallResult(data: UserAccountResponse) {
                return localDataSource.insertUser(data.toEntity())
            }

        }.asFlow()

    override fun updateUsername(newUsername: String): Flow<Resource<Unit>> =
        object : NetworkBoundRequest<UserAccountResponse>() {
            override suspend fun createCall(): Flow<FirebaseResponse<UserAccountResponse>> {
                return remoteDataSource.updateUsername(newUsername)
            }

            override suspend fun saveCallResult(data: UserAccountResponse) {
                localDataSource.insertUser(data.toEntity())
            }

        }.asFlow()

    override fun logOut() {
        remoteDataSource.logOut()
    }
}