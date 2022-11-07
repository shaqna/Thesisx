package com.ngedev.thesisx.domain.di

import com.ngedev.thesisx.domain.usecase.detail.thesis_detail.DetailInteractor
import com.ngedev.thesisx.domain.usecase.detail.thesis_detail.DetailUseCase
import com.ngedev.thesisx.ui.detail.thesis_detail.DetailViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModelOf

val detailModule = module {
    factoryOf(::DetailInteractor) {
        bind<DetailUseCase>()
    }

    viewModelOf(::DetailViewModel)
}