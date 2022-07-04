package com.ngedev.thesisx.domain.di

import com.ngedev.thesisx.domain.usecase.loanDetail.LoanDetailInteractor
import com.ngedev.thesisx.domain.usecase.loanDetail.LoanDetailUseCase
import com.ngedev.thesisx.ui.loanDetail.LoanDetailViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModelOf

val loanDetailModule = module {
    factoryOf(::LoanDetailInteractor) {
        bind<LoanDetailUseCase>()
    }

    viewModelOf(::LoanDetailViewModel)
}