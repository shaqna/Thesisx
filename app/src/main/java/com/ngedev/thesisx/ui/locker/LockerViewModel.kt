package com.ngedev.thesisx.ui.locker

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.LockerKey
import com.ngedev.thesisx.domain.usecase.locker.LockerUseCase

class LockerViewModel(private val useCase: LockerUseCase): ViewModel() {
    fun getKey(): LiveData<Resource<LockerKey>> =
        useCase.getNewLockerKey().asLiveData()


}