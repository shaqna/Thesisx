package com.ngedev.thesisx.domain.di

import com.ngedev.thesisx.domain.usecase.home.HomeInteractor
import com.ngedev.thesisx.domain.usecase.home.HomeUseCase
import com.ngedev.thesisx.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val homeModule = module {
    factoryOf(::HomeInteractor) {
        bind<HomeUseCase>()
    }

    viewModelOf(::HomeViewModel)
}