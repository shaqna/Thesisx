package com.ngedev.thesisx.domain.usecase.loan

import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Loan
import com.ngedev.thesisx.domain.model.User
import kotlinx.coroutines.flow.Flow

interface BorrowUseCase {

    fun getAllBorrowing(borrowingIds: List<String>): Flow<Resource<List<Loan>>>

    fun getCurrentUser(): Flow<Resource<User>>

    fun deleteLoan(id: String):Flow<Resource<Unit>>

    fun refreshUser(): Flow<Resource<Unit>>

    fun updateThesisAvailability(state: Boolean, thesisId: String): Flow<Unit>

}