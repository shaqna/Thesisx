package com.ngedev.thesisx.data.repository


import com.ngedev.thesisx.data.mapper.toModel
import com.ngedev.thesisx.data.source.remote.RemoteDataSource
import com.ngedev.thesisx.data.source.remote.network.FirebaseResponse
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.CupBoard
import com.ngedev.thesisx.domain.repository.ICupBoardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CupBoardRepositoryImpl(private val remote: RemoteDataSource): ICupBoardRepository {

    override fun getLockerKey(): Flow<Resource<CupBoard>> =
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