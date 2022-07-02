package com.ngedev.thesisx.domain.usecase.locker

import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.LockerKey
import kotlinx.coroutines.flow.Flow

interface LockerUseCase {
    fun getNewLockerKey(): Flow<Resource<LockerKey>>
}