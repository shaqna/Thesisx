package com.ngedev.thesisx.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.domain.usecase.search.SearchUseCase

class SearchViewModel(private val useCase: SearchUseCase) : ViewModel() {

    fun searchThesisByTitle(title: String): LiveData<Resource<List<Thesis>>> {
        return useCase.searchThesis(title).asLiveData()
    }

}