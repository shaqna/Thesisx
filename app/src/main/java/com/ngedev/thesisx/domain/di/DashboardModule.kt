package com.ngedev.thesisx.domain.di

import com.ngedev.thesisx.domain.usecase.search.SearchInteractor
import com.ngedev.thesisx.domain.usecase.search.SearchUseCase
import com.ngedev.thesisx.ui.search.SearchViewModel
import com.ngedev.thesisx.domain.usecase.home.HomeInteractor
import com.ngedev.thesisx.domain.usecase.home.HomeUseCase
import com.ngedev.thesisx.ui.home.HomeViewModel
import com.ngedev.thesisx.domain.usecase.favorite.BookmarkInteractor
import com.ngedev.thesisx.domain.usecase.favorite.BookmarkUseCase
import com.ngedev.thesisx.ui.favorite.FavoriteViewModel
import com.ngedev.thesisx.domain.usecase.settings.SettingsInteractor
import com.ngedev.thesisx.domain.usecase.settings.SettingsUseCase
import com.ngedev.thesisx.ui.settings.SettingsViewModel
import com.ngedev.thesisx.domain.usecase.loan.BorrowInteractor
import com.ngedev.thesisx.domain.usecase.loan.BorrowUseCase
import com.ngedev.thesisx.domain.usecase.splash.SplashInteractor
import com.ngedev.thesisx.domain.usecase.splash.SplashUseCase
import com.ngedev.thesisx.ui.loan.LoanViewModel
import com.ngedev.thesisx.ui.splash.SplashViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModelOf

val splashModule = module {
    factoryOf(::SplashInteractor) {
        bind<SplashUseCase>()
    }

    viewModelOf(::SplashViewModel)
}

val homeModule = module {
    factoryOf(::HomeInteractor) {
        bind<HomeUseCase>()
    }

    viewModelOf(::HomeViewModel)
}

val searchModule = module {
    factoryOf(::SearchInteractor) {
        bind<SearchUseCase>()
    }

    viewModelOf(::SearchViewModel)
}

val favoriteModule = module {
    factoryOf(::BookmarkInteractor) {
        bind<BookmarkUseCase>()
    }

    viewModelOf(::FavoriteViewModel)
}

val borrowModule = module {
    factoryOf(::BorrowInteractor) {
        bind<BorrowUseCase>()
    }

    viewModelOf(::LoanViewModel)
}

val settingsModule = module {
    factoryOf(::SettingsInteractor) {
        bind<SettingsUseCase>()
    }

    viewModelOf(::SettingsViewModel)
}

