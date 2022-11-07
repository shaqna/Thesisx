package com.ngedev.thesisx.domain.usecase.detail.loan_detail

import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Loan
import kotlinx.coroutines.flow.Flow

interface LoanDetailUseCase {
    fun updateThesisAvailability(state: Boolean, thesisId: String): Flow<Unit>
    fun endLoan(id: String): Flow<Resource<Unit>>
    fun getLoanById(id: String): Flow<Resource<Loan>>
}