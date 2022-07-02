package com.ngedev.thesisx.data.repository

import com.ngedev.thesisx.data.mapper.toModel
import com.ngedev.thesisx.data.source.remote.RemoteDataSource
import com.ngedev.thesisx.data.source.remote.network.FirebaseResponse
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.LockerKey
import com.ngedev.thesisx.domain.repository.ILockerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LockerRepositoryImpl(private val remote: RemoteDataSource): ILockerRepository {
    override fun modifyLockerValue(key: String): Flow<Resource<Unit>> =
        flow {
            emit(Resource.Loading())
            remote.modifyLockerKey(key).collect { response ->

                when(response) {
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

    override fun getLockerKey(): Flow<Resource<LockerKey>> =
       flow {
           emit(Resource.Loading())
           remote.getKey().collect { response ->
               when(response) {
                   is FirebaseResponse.Success -> {
                       emit(Resource.Success(response.data.toModel()))
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

}