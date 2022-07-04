package com.ngedev.thesisx.domain.usecase.loanDetail

import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.domain.model.UserAccount
import com.ngedev.thesisx.domain.repository.IThesisRepository
import kotlinx.coroutines.flow.Flow

class LoanDetailInteractor(private val repository: IThesisRepository): LoanDetailUseCase {
    override fun loanComplete(id: String): Flow<Resource<Unit>> {
        return repository.deleteBorrowingThesis(id)
    }

    override fun getThesisById(id: String): Flow<Resource<Thesis>> {
        return repository.getThesisById(id)
    }

    override fun addFavoriteThesis(id: String): Flow<Resource<Unit>> {
        return repository.addFavoriteThesis(id)
    }

    override fun deleteFavoriteThesis(id: String): Flow<Resource<Unit>> {
        return repository.deleteFavoriteThesis(id)
    }

    override fun getCurrentUser(): Flow<Resource<UserAccount>> {
        return repository.getCurrentUser()
    }

    override fun changeStateBorrow(state: Boolean, thesisId: String): Flow<Resource<Unit>> {
        return repository.modifyValue(state, thesisId)
    }
}