package com.ngedev.thesisx.data.repository

import com.ngedev.thesisx.data.cache.NetworkBoundRequest
import com.ngedev.thesisx.data.source.local.LocalDataSource
import com.ngedev.thesisx.data.mapper.toEntity
import com.ngedev.thesisx.domain.model.User
import com.ngedev.thesisx.data.source.remote.response.UserResponse
import com.ngedev.thesisx.data.source.remote.RemoteDataSource
import com.ngedev.thesisx.data.source.remote.network.FirebaseResponse
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IAuthRepository {
    override fun signIn(email: String, password: String): Flow<Resource<Unit>> =
        object : NetworkBoundRequest<UserResponse>() {
            override suspend fun createCall(): Flow<FirebaseResponse<UserResponse>> =
                remoteDataSource.signIn(email, password)

            override suspend fun saveCallResult(data: UserResponse) {
                localDataSource.clearUser()
                localDataSource.insertUser(data.toEntity())
            }

        }.asFlow()

    override fun signUp(user: User, email: String, password: String): Flow<Resource<Unit>> =
        flow {
            emit(Resource.Loading())
            remoteDataSource.signUp(email, password, user).collect { response ->
                when (response) {
                    is FirebaseResponse.Success -> {
                        emit(Resource.Success(null))
                    }

                    is FirebaseResponse.Error -> {

                        emit(Resource.Error(response.errorMessage))
                    }

                    is FirebaseResponse.Empty -> {
                        emit(Resource.Error("Empty"))
                    }
                }

            }
        }

    override fun resetPassword(email: String): Flow<Resource<Unit>> =
        object : NetworkBoundRequest<Unit>() {
            override suspend fun createCall(): Flow<FirebaseResponse<Unit>> {
                return remoteDataSource.resetPassword(email)
            }

            override suspend fun saveCallResult(data: Unit) {}

        }.asFlow()

}