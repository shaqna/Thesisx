package com.ngedev.thesisx.domain.usecase.detail.thesis_detail

import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.domain.model.User
import com.ngedev.thesisx.domain.repository.IThesisRepository
import kotlinx.coroutines.flow.Flow

class DetailInteractor(
    private val thesisRepository: IThesisRepository
) : DetailUseCase {
    override fun getThesisById(id: String): Flow<Resource<Thesis>> {
        return thesisRepository.getThesisById(id)
    }

    override fun addFavoriteThesis(id: String): Flow<Resource<Unit>> {
        return thesisRepository.addFavoriteThesis(id)
    }

    override fun deleteFavoriteThesis(id: String): Flow<Resource<Unit>> {
        return thesisRepository.deleteFavoriteThesis(id)
    }

    override fun getCurrentUser(): Flow<Resource<User>> {
        return thesisRepository.getCurrentUser()
    }

}