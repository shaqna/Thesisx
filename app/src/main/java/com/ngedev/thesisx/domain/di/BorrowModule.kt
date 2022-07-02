package com.ngedev.thesisx.domain.di

import com.ngedev.thesisx.domain.usecase.borrow.BorrowInteractor
import com.ngedev.thesisx.domain.usecase.borrow.BorrowUseCase
import com.ngedev.thesisx.ui.borrow.BorrowViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModelOf

val borrowModule = module {
    factoryOf(::BorrowInteractor) {
        bind<BorrowUseCase>()
    }

    viewModelOf(::BorrowViewModel)
}