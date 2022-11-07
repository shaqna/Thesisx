package com.ngedev.thesisx.data.repository

import android.net.Uri
import android.util.Log
import com.ngedev.thesisx.data.cache.NetworkBoundDelete
import com.ngedev.thesisx.data.cache.NetworkBoundRequest
import com.ngedev.thesisx.data.cache.NetworkBoundResource
import com.ngedev.thesisx.data.mapper.toEntity
import com.ngedev.thesisx.data.mapper.toFlowModel
import com.ngedev.thesisx.data.mapper.toListEntity
import com.ngedev.thesisx.data.mapper.toListFlowModel
import com.ngedev.thesisx.data.source.local.LocalDataSource
import com.ngedev.thesisx.data.source.remote.RemoteDataSource
import com.ngedev.thesisx.data.source.remote.network.FirebaseResponse
import com.ngedev.thesisx.data.source.remote.response.LoanResponse
import com.ngedev.thesisx.data.source.remote.response.UserResponse
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Loan
import com.ngedev.thesisx.domain.model.User
import com.ngedev.thesisx.domain.repository.ILoanRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class LoanRepositoryImpl(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource
): ILoanRepository {
    override fun uploadForm(form: Loan, imageUri: Uri): Flow<Resource<Unit>> =
        object : NetworkBoundRequest<LoanResponse>() {
            override suspend fun createCall(): Flow<FirebaseResponse<LoanResponse>> {
                return remote.insertForm(form, imageUri,getCurrentUserId())
            }

            override suspend fun saveCallResult(data: LoanResponse) {
                local.insertLoan(data.toEntity())
            }

        }.asFlow()

    override fun deleteLoan(id: String): Flow<Resource<Unit>> =
        object : NetworkBoundDelete() {
            override suspend fun deleteFromNetwork(): Flow<FirebaseResponse<Unit>> {
                return remote.deleteForm(id, getCurrentUserId())
            }

            override suspend fun onDeleteSuccess() {
                local.deleteLoan(id)
            }

        }.asFlow()

    override fun getLoanById(id: String): Flow<Resource<Loan>> =
        object : NetworkBoundResource<Loan, LoanResponse>() {
            override fun loadFromDB(): Flow<Loan?> {
                Log.d("MyLog", local.selectLoanById(id).toFlowModel().toString())
                return local.selectLoanById(id).toFlowModel()
            }

            override fun shouldFetch(data: Loan?): Boolean {
                return data == null
            }

            override suspend fun createCall(): Flow<FirebaseResponse<LoanResponse>> {
                return remote.getLoanById(id)
            }

            override suspend fun saveCallResult(data: LoanResponse) {
                local.insertLoan(data.toEntity())
            }

        }.asFlow()


    override fun getMyLoan(ids: List<String>): Flow<Resource<List<Loan>>> =
        object: NetworkBoundResource<List<Loan>, List<LoanResponse>>() {
            override fun loadFromDB(): Flow<List<Loan>?> =
                local.selectAllLoan().toListFlowModel()

            override fun shouldFetch(data: List<Loan>?): Boolean =
                data == null

            override suspend fun createCall(): Flow<FirebaseResponse<List<LoanResponse>>> =
                remote.getMyLoan(ids)

            override suspend fun saveCallResult(data: List<LoanResponse>) {
                local.insertLoans(data.toListEntity())
            }

        }.asFlow()


        override fun getCurrentUser(): Flow<Resource<User>> =
       flow {
           val userId = getCurrentUserId()
           if(userId.isNotEmpty()){
               emitAll(getUser(userId))
           }
        }

    override fun updateThesisAvailability(state: Boolean, thesisId: String): Flow<Unit> =
        remote.updateThesisAvailability(state, thesisId)

    override fun getCurrentUserId(): String = remote.getCurrentUserId()

    override fun getUser(id: String): Flow<Resource<User>> =
        object : NetworkBoundResource<User, UserResponse>(){
            override fun loadFromDB(): Flow<User> {
                return local.selectUser().toFlowModel()
            }

            override fun shouldFetch(data: User?): Boolean {
                return data == null
            }

            override suspend fun createCall(): Flow<FirebaseResponse<UserResponse>> {
                return remote.getCurrentUser(id)
            }

            override suspend fun saveCallResult(data: UserResponse) {
                local.insertUser(data.toEntity())
            }

        }.asFlow()

    override fun refreshUser(): Flow<Resource<Unit>> =
        object : NetworkBoundRequest<UserResponse>() {

            override suspend fun createCall(): Flow<FirebaseResponse<UserResponse>> =
                remote.getCurrentUser(getCurrentUserId())

            override suspend fun saveCallResult(data: UserResponse) =
                local.insertUser(data.toEntity())

        }.asFlow()




//    override suspend fun clearLoanWhereIsDeleted() {
//        local.clearLoanWHereIsDeleted()
//    }
}