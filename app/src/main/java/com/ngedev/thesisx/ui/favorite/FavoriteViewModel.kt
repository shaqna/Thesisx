package com.ngedev.thesisx.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.domain.model.User
import com.ngedev.thesisx.domain.usecase.favorite.BookmarkUseCase

class FavoriteViewModel(private val useCase: BookmarkUseCase) : ViewModel() {

    fun getAllBookmarkedThesis(favoriteIds: List<String>): LiveData<Resource<List<Thesis>>> {
        return useCase.getAllBookmarked(favoriteIds).asLiveData()
    }

    fun currentUser(): LiveData<Resource<User>> {
        return useCase.getCurrentUser().asLiveData()
    }



}