package com.ngedev.thesisx.domain.repository

import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.LockerKey
import kotlinx.coroutines.flow.Flow

interface ILockerRepository {
    fun modifyLockerValue(key: String): Flow<Resource<Unit>>
    fun getLockerKey(): Flow<Resource<LockerKey>>
}