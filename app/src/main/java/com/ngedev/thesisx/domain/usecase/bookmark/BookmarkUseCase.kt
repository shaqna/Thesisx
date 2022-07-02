package com.ngedev.thesisx.domain.usecase.bookmark

import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.domain.model.UserAccount
import kotlinx.coroutines.flow.Flow

interface BookmarkUseCase {

    fun getAllBookmarked(favoriteIds: List<String>): Flow<Resource<List<Thesis>>>

    fun getCurrentUser(): Flow<Resource<UserAccount>>

}