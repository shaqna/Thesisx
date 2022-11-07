package com.ngedev.thesisx.domain.usecase.favorite

import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.domain.model.User
import com.ngedev.thesisx.domain.repository.IThesisRepository
import kotlinx.coroutines.flow.Flow

class BookmarkInteractor(private val repository: IThesisRepository): BookmarkUseCase {
    override fun getAllBookmarked(favoriteIds: List<String>): Flow<Resource<List<Thesis>>> {
        return repository.getAllFavorites(favoriteIds)
    }

    override fun getCurrentUser(): Flow<Resource<User>> {
        return repository.getCurrentUser()
    }

}