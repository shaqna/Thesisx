package com.ngedev.thesisx.domain.usecase.borrow

import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.domain.model.UserAccount
import kotlinx.coroutines.flow.Flow

interface BorrowUseCase {

    fun getAllBorrowing(borrowingIds: List<String>): Flow<Resource<List<Thesis>>>

    fun getCurrentUser(): Flow<Resource<UserAccount>>

}