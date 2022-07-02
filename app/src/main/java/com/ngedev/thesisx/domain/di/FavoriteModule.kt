package com.ngedev.thesisx.domain.di

import com.ngedev.thesisx.domain.usecase.bookmark.BookmarkInteractor
import com.ngedev.thesisx.domain.usecase.bookmark.BookmarkUseCase
import com.ngedev.thesisx.ui.bookmark.BookmarkViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModelOf

val favoriteModule = module {
    factoryOf(::BookmarkInteractor) {
        bind<BookmarkUseCase>()
    }

    viewModelOf(::BookmarkViewModel)
}