package com.ngedev.thesisx.ui.detail.thesis_detail

import androidx.lifecycle.*
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.domain.model.User
import com.ngedev.thesisx.domain.usecase.detail.thesis_detail.DetailUseCase
import kotlinx.coroutines.launch

class DetailViewModel(private val useCase: DetailUseCase): ViewModel() {

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite = _isFavorite

    private val _isBorrowed = MutableLiveData<Boolean>()
    val isBorrowed = _isBorrowed

    fun setFavorite(state: Boolean) {
        viewModelScope.launch {
            _isFavorite.value = state
        }
    }

    fun setBorrow(state: Boolean) {
        viewModelScope.launch {
            _isBorrowed.value = state
        }
    }

    fun getThesisById(id: String): LiveData<Resource<Thesis>> {
        return useCase.getThesisById(id).asLiveData()
    }

    fun addFavoriteThesis(id: String): LiveData<Resource<Unit>> {
        return useCase.addFavoriteThesis(id).asLiveData()
    }


    fun deleteFavoriteThesis(id: String): LiveData<Resource<Unit>> {
        return useCase.deleteFavoriteThesis(id).asLiveData()
    }

    fun getCurrentUser(): LiveData<Resource<User>> {
        return useCase.getCurrentUser().asLiveData()
    }



}