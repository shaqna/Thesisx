package com.ngedev.thesisx.domain.usecase.splash

import kotlinx.coroutines.flow.Flow

interface SplashUseCase {

    suspend fun clearLoanWhenIsDeleted()
}