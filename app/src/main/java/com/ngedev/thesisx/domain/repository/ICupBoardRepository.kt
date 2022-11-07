package com.ngedev.thesisx.domain.repository

import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.CupBoard
import kotlinx.coroutines.flow.Flow

interface ICupBoardRepository {
    fun getLockerKey(): Flow<Resource<CupBoard>>
}