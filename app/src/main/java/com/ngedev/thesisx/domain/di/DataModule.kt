package com.ngedev.thesisx.domain.di

import androidx.room.Room
import com.ngedev.thesisx.data.repository.*
import com.ngedev.thesisx.data.source.local.LocalDataSource
import com.ngedev.thesisx.data.source.local.LocalDatabase
import com.ngedev.thesisx.data.source.remote.RemoteDataSource
import com.ngedev.thesisx.data.source.remote.service.*
import com.ngedev.thesisx.domain.repository.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            LocalDatabase::class.java,
            "Thesis.db"
        ).fallbackToDestructiveMigration().build()
    }

    factory {
        get<LocalDatabase>().userDao()
    }

    factory {
        get<LocalDatabase>().thesisDao()
    }

    factory {
        get<LocalDatabase>().loanDao()
    }
}

val serviceModule = module {
    factoryOf(::AuthService)
    factoryOf(::UserService)
    factoryOf(::ThesisService)
    factoryOf(::CupBoardService)
    factoryOf(::LoanService)

}

val dataSourceModule = module {
    singleOf(::LocalDataSource)
    singleOf(::RemoteDataSource)
}

val repositoryModule = module {
    singleOf(::AuthRepositoryImpl) { bind<IAuthRepository>() }
    singleOf(::UserRespositoryImpl) { bind <IUserRepository>() }
    singleOf(::ThesisRepositoryImpl) { bind<IThesisRepository>() }
    singleOf(::CupBoardRepositoryImpl) { bind<ICupBoardRepository>() }
    singleOf(::LoanRepositoryImpl) { bind<ILoanRepository>()}
}