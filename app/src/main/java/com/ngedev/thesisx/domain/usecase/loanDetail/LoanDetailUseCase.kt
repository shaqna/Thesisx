package com.ngedev.thesisx.domain.usecase.loanDetail

import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.domain.model.UserAccount
import kotlinx.coroutines.flow.Flow

interface LoanDetailUseCase {

    fun loanComplete(id: String): Flow<Resource<Unit>>
    fun getThesisById(id: String): Flow<Resource<Thesis>>
    fun addFavoriteThesis(id: String): Flow<Resource<Unit>>
    fun deleteFavoriteThesis(id: String): Flow<Resource<Unit>>
    fun getCurrentUser(): Flow<Resource<UserAccount>>
    fun changeStateBorrow(state: Boolean, thesisId: String): Flow<Resource<Unit>>
}