package com.ngedev.thesisx.domain.di


import com.ngedev.thesisx.domain.usecase.form.LoanFormInteractor
import com.ngedev.thesisx.domain.usecase.form.LoanFormUseCase
import com.ngedev.thesisx.ui.form.LoanFormViewModel
import com.ngedev.thesisx.domain.usecase.detail.loan_detail.LoanDetailInteractor
import com.ngedev.thesisx.domain.usecase.detail.loan_detail.LoanDetailUseCase
import com.ngedev.thesisx.ui.detail.loan_detail.LoanDetailViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModelOf


val loanFormModule = module {
    factoryOf(::LoanFormInteractor) { bind<LoanFormUseCase>() }
    viewModelOf(::LoanFormViewModel)
}

val loanDetailModule =  module {
    factoryOf(::LoanDetailInteractor) {
        bind<LoanDetailUseCase>()
    }
    viewModelOf(::LoanDetailViewModel)
}