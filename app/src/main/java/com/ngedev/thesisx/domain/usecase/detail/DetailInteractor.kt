package com.ngedev.thesisx.domain.usecase.detail

import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.domain.model.UserAccount
import com.ngedev.thesisx.domain.repository.ILockerRepository
import com.ngedev.thesisx.domain.repository.IThesisRepository
import kotlinx.coroutines.flow.Flow

class DetailInteractor(
    private val thesisRepository: IThesisRepository,
    private val lockerRepository: ILockerRepository
) : DetailUseCase {
    override fun getThesisById(id: String): Flow<Resource<Thesis>> {
        return thesisRepository.getThesisById(id)
    }

    override fun addBorrowingThesis(id: String): Flow<Resource<Unit>> {
        return thesisRepository.addBorrowingThesis(id)
    }

    override fun addFavoriteThesis(id: String): Flow<Resource<Unit>> {
        return thesisRepository.addFavoriteThesis(id)
    }

    override fun deleteFavoriteThesis(id: String): Flow<Resource<Unit>> {
        return thesisRepository.deleteFavoriteThesis(id)
    }

    override fun getCurrentUser(): Flow<Resource<UserAccount>> {
        return thesisRepository.getCurrentUser()
    }

    override fun changeStateBorrow(state: Boolean, thesisId: String): Flow<Resource<Unit>> {
        return thesisRepository.modifyValue(state, thesisId)
    }

    override fun modifyKeyValue(key: String): Flow<Resource<Unit>> {
        return lockerRepository.modifyLockerValue(key)
    }

}