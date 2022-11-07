package com.ngedev.thesisx.domain.usecase.splash

import com.ngedev.thesisx.domain.repository.ILoanRepository
import kotlinx.coroutines.flow.Flow

class SplashInteractor(private val loanRepository: ILoanRepository): SplashUseCase {
    override suspend fun clearLoanWhenIsDeleted(){
//        loanRepository.clearLoanWhereIsDeleted()
    }

}