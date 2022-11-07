package com.ngedev.thesisx.domain.usecase.cupboard

import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.CupBoard
import kotlinx.coroutines.flow.Flow

interface CupBoardUseCase {
    fun getNewLockerKey(): Flow<Resource<CupBoard>>
}