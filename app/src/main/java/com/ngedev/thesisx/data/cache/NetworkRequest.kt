package com.ngedev.thesisx.data.cache

import com.ngedev.thesisx.data.source.remote.network.FirebaseResponse
import com.ngedev.thesisx.domain.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

abstract class NetworkRequest<ResultType, RequestType> {
    private val result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        when(val response = createCall().first()) {
            is FirebaseResponse.Success -> {
                emit(Resource.Success(fetchResult(response.data)))
                onFetchSuccess()
            }
            is FirebaseResponse.Error -> {
                emit(Resource.Error(response.errorMessage))
            }
            else -> {}
        }
    }

    protected abstract suspend fun createCall(): Flow<FirebaseResponse<RequestType>>

    protected abstract fun fetchResult(result: RequestType): ResultType

    protected open suspend fun onFetchSuccess() {}

    protected open suspend fun onFetchFailed() {}

    fun asFlow() = result
}