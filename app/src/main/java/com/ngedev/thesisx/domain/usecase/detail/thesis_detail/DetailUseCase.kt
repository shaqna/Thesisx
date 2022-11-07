package com.ngedev.thesisx.domain.usecase.detail.thesis_detail

import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.domain.model.User
import kotlinx.coroutines.flow.Flow

interface DetailUseCase {
    fun getThesisById(id: String): Flow<Resource<Thesis>>
    fun addFavoriteThesis(id: String): Flow<Resource<Unit>>
    fun deleteFavoriteThesis(id: String): Flow<Resource<Unit>>
    fun getCurrentUser(): Flow<Resource<User>>
}