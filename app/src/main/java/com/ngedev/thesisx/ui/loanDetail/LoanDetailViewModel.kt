package com.ngedev.thesisx.ui.loanDetail

import androidx.lifecycle.*
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.domain.model.UserAccount
import com.ngedev.thesisx.domain.usecase.loanDetail.LoanDetailUseCase
import kotlinx.coroutines.launch

class LoanDetailViewModel(private val useCase: LoanDetailUseCase) : ViewModel() {

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite = _isFavorite

    fun setFavorite(state: Boolean) {
        viewModelScope.launch {
            _isFavorite.value = state
        }
    }

    fun loanComplete(id: String): LiveData<Resource<Unit>> {
        return useCase.loanComplete(id).asLiveData()
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

    fun getCurrentUser(): LiveData<Resource<UserAccount>> {
        return useCase.getCurrentUser().asLiveData()
    }

    fun changeStateBorrow(state: Boolean, id: String): LiveData<Resource<Unit>> {
        return useCase.changeStateBorrow(state, id).asLiveData()
    }

}