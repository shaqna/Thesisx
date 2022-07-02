package com.ngedev.thesisx.domain.usecase.locker


import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.LockerKey
import com.ngedev.thesisx.domain.repository.ILockerRepository
import kotlinx.coroutines.flow.Flow

class LockerInteractor(private val repository: ILockerRepository): LockerUseCase {
    override fun getNewLockerKey(): Flow<Resource<LockerKey>> {
        return repository.getLockerKey()
    }

}