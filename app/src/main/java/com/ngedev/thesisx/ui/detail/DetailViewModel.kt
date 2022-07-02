package com.ngedev.thesisx.ui.detail

import androidx.lifecycle.*
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.domain.model.UserAccount
import com.ngedev.thesisx.domain.usecase.detail.DetailUseCase
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

    fun addBorrowingThesis(id: String): LiveData<Resource<Unit>> {
        return useCase.addBorrowingThesis(id).asLiveData()
    }

    fun deleteFavoriteThesis(id: String): LiveData<Resource<Unit>> {
        return useCase.deleteFavoriteThesis(id).asLiveData()
    }

    fun deleteBorrowingThesis(id: String): LiveData<Resource<Unit>> {
        return useCase.deleteBorrowingThesis(id).asLiveData()
    }

    fun getCurrentUser(): LiveData<Resource<UserAccount>> {
        return useCase.getCurrentUser().asLiveData()
    }

    fun changeStateBorrow(state: Boolean, id: String): LiveData<Resource<Unit>> {
        return useCase.changeStateBorrow(state, id).asLiveData()
    }

    fun updateKey(key: String): LiveData<Resource<Unit>> = useCase.modifyKeyValue(key).asLiveData()

}