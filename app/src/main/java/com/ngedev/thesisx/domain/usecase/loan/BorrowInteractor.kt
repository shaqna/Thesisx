package com.ngedev.thesisx.domain.usecase.loan

import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Loan
import com.ngedev.thesisx.domain.model.User
import com.ngedev.thesisx.domain.repository.ILoanRepository
import kotlinx.coroutines.flow.Flow

class BorrowInteractor(private val repository: ILoanRepository) : BorrowUseCase {
    override fun getAllBorrowing(borrowingIds: List<String>): Flow<Resource<List<Loan>>> =
        repository.getMyLoan(borrowingIds)


    override fun getCurrentUser(): Flow<Resource<User>> =
        repository.getCurrentUser()


    override fun deleteLoan(id: String): Flow<Resource<Unit>> =
        repository.deleteLoan(id)

    override fun refreshUser(): Flow<Resource<Unit>> =
        repository.refreshUser()

    override fun updateThesisAvailability(state: Boolean, thesisId: String): Flow<Unit> =
        repository.updateThesisAvailability(state, thesisId)


}