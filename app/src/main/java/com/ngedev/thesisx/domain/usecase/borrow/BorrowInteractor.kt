package com.ngedev.thesisx.domain.usecase.borrow

import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.domain.model.UserAccount
import com.ngedev.thesisx.domain.repository.IThesisRepository
import kotlinx.coroutines.flow.Flow

class BorrowInteractor(private val repository: IThesisRepository): BorrowUseCase {
    override fun getAllBorrowing(borrowingIds: List<String>): Flow<Resource<List<Thesis>>> {
        return repository.getAllBorrowing(borrowingIds)
    }

    override fun getCurrentUser(): Flow<Resource<UserAccount>> {
        return repository.getCurrentUser()
    }


}