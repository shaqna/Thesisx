package com.ngedev.thesisx.ui.loan

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Loan
import com.ngedev.thesisx.domain.model.User
import com.ngedev.thesisx.domain.usecase.loan.BorrowUseCase

class LoanViewModel(private val useCase: BorrowUseCase) : ViewModel() {

    fun getAllUserThesisBorrow(ids: List<String>): LiveData<Resource<List<Loan>>> =
        useCase.getAllBorrowing(ids).asLiveData()

    fun currentUser(): LiveData<Resource<User>> = useCase.getCurrentUser().asLiveData()

    fun deleteLoan(id: String) = useCase.deleteLoan(id).asLiveData()

    fun refreshUser(): LiveData<Resource<Unit>> = useCase.refreshUser().asLiveData()

    fun changeStateBorrow(state: Boolean, id: String): LiveData<Unit> {
        return useCase.updateThesisAvailability(state, id).asLiveData()
    }

}