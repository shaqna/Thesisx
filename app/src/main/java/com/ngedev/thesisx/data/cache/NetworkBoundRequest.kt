package com.ngedev.thesisx.data.cache

import com.ngedev.thesisx.data.source.remote.network.FirebaseResponse
import com.ngedev.thesisx.domain.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

abstract class NetworkBoundRequest<RequestType> {
    private val result: Flow<Resource<Unit>> = flow {
        preRequest()
        emit(Resource.Loading())
        when (val firebaseResponse = createCall().first()) {
            is FirebaseResponse.Success -> {
                saveCallResult(firebaseResponse.data)
                emit(Resource.Success(null))
            }
            is FirebaseResponse.Error -> {

                emit(Resource.Error(firebaseResponse.errorMessage))
            }

            is FirebaseResponse.Empty -> {
                emit(Resource.Error("Empty"))
            }
        }

    }

    protected open suspend fun preRequest() {}

    protected abstract suspend fun createCall(): Flow<FirebaseResponse<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow() = result
}