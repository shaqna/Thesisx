package com.ngedev.thesisx.domain.di

import com.ngedev.thesisx.domain.usecase.cupboard.CupBoardInteractor
import com.ngedev.thesisx.domain.usecase.cupboard.CupBoardUseCase
import com.ngedev.thesisx.ui.cupboard.CupBoardViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf


val cupBoardModule = module {
    factoryOf(::CupBoardInteractor){bind<CupBoardUseCase>()}
    viewModelOf(::CupBoardViewModel)
}