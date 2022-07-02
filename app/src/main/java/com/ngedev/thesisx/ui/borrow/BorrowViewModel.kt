package com.ngedev.thesisx.ui.borrow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.domain.model.UserAccount
import com.ngedev.thesisx.domain.usecase.borrow.BorrowUseCase

class BorrowViewModel(private val useCase: BorrowUseCase) : ViewModel() {

    fun getAllUserThesisBorrow(ids: List<String>): LiveData<Resource<List<Thesis>>> =
        useCase.getAllBorrowing(ids).asLiveData()

    fun currentUser(): LiveData<Resource<UserAccount>> = useCase.getCurrentUser().asLiveData()

}