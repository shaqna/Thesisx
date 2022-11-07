package com.ngedev.thesisx.domain.usecase.detail.loan_detail

import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Loan
import com.ngedev.thesisx.domain.repository.ILoanRepository
import kotlinx.coroutines.flow.Flow

class LoanDetailInteractor(private val loanRepository: ILoanRepository): LoanDetailUseCase {
    override fun endLoan(id: String): Flow<Resource<Unit>> {
        return loanRepository.deleteLoan(id)
    }

    override fun getLoanById(id: String): Flow<Resource<Loan>> {
        return loanRepository.getLoanById(id)
    }

    override fun updateThesisAvailability(state: Boolean, thesisId: String): Flow<Unit> {
        return loanRepository.updateThesisAvailability(state, thesisId)
    }

}