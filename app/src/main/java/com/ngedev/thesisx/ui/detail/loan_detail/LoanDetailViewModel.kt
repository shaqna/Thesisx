package com.ngedev.thesisx.ui.detail.loan_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Loan
import com.ngedev.thesisx.domain.usecase.detail.loan_detail.LoanDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoanDetailViewModel(private val useCase: LoanDetailUseCase): ViewModel() {

    private var _loadingState = MutableStateFlow<Resource<Unit>>(Resource.Loading())
    var loadingState = _loadingState

    fun getLoanById(id: String): LiveData<Resource<Loan>> = useCase.getLoanById(id).asLiveData()

    fun endForm(id: String) = viewModelScope.launch {
        useCase.endLoan(id).collect {
            when(it) {
                is Resource.Loading -> {
                    _loadingState.value = Resource.Loading()
                }

                is Resource.Success -> {
                    _loadingState.value = Resource.Success(null)
                }

                is Resource.Error -> {
                    _loadingState.value = Resource.Error(it.message.toString())
                }

            }
        }
    }


    fun changeStateBorrow(state: Boolean, id: String): LiveData<Unit> {
        return useCase.updateThesisAvailability(state, id).asLiveData()
    }
}