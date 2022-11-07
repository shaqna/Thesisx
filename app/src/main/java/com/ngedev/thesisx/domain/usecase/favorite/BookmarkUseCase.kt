package com.ngedev.thesisx.domain.usecase.favorite

import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.domain.model.User
import kotlinx.coroutines.flow.Flow

interface BookmarkUseCase {

    fun getAllBookmarked(favoriteIds: List<String>): Flow<Resource<List<Thesis>>>

    fun getCurrentUser(): Flow<Resource<User>>

}