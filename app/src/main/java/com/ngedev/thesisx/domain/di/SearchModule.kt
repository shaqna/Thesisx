package com.ngedev.thesisx.domain.di

import com.ngedev.thesisx.domain.usecase.search.SearchInteractor
import com.ngedev.thesisx.domain.usecase.search.SearchUseCase
import com.ngedev.thesisx.ui.search.SearchViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModelOf

val searchModule = module {
    factoryOf(::SearchInteractor) {
        bind<SearchUseCase>()
    }

    viewModelOf(::SearchViewModel)
}