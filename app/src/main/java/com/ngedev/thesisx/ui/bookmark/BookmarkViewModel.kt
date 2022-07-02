package com.ngedev.thesisx.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.domain.model.UserAccount
import com.ngedev.thesisx.domain.usecase.bookmark.BookmarkUseCase

class BookmarkViewModel(private val useCase: BookmarkUseCase) : ViewModel() {

    fun getAllBookmarkedThesis(favoriteIds: List<String>): LiveData<Resource<List<Thesis>>> {
        return useCase.getAllBookmarked(favoriteIds).asLiveData()
    }

    fun currentUser(): LiveData<Resource<UserAccount>> {
        return useCase.getCurrentUser().asLiveData()
    }



}