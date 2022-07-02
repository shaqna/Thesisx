package com.ngedev.thesisx.domain.di

import androidx.room.Room
import com.ngedev.thesisx.data.source.local.LocalDataSource
import com.ngedev.thesisx.data.source.local.LocalDatabase
import com.ngedev.thesisx.data.source.remote.RemoteDataSource
import com.ngedev.thesisx.data.source.remote.service.AuthService
import com.ngedev.thesisx.data.repository.AuthRepositoryImpl
import com.ngedev.thesisx.data.repository.LockerRepositoryImpl
import com.ngedev.thesisx.data.repository.ThesisRepositoryImpl
import com.ngedev.thesisx.data.repository.UserRespositoryImpl
import com.ngedev.thesisx.data.source.remote.service.LockerService
import com.ngedev.thesisx.data.source.remote.service.ThesisService
import com.ngedev.thesisx.data.source.remote.service.UserService
import com.ngedev.thesisx.domain.repository.IAuthRepository
import com.ngedev.thesisx.domain.repository.ILockerRepository
import com.ngedev.thesisx.domain.repository.IThesisRepository
import com.ngedev.thesisx.domain.repository.IUserRepository
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
        ).build()
    }

    factory {
        get<LocalDatabase>().userDao()
    }

    factory {
        get<LocalDatabase>().thesisDao()
    }
}

val serviceModule = module {
    factoryOf(::AuthService)
    factoryOf(::UserService)
    factoryOf(::ThesisService)
    factoryOf(::LockerService)

}

val dataSourceModule = module {
    singleOf(::LocalDataSource)
    singleOf(::RemoteDataSource)
}

val repositoryModule = module {
    singleOf(::AuthRepositoryImpl) { bind<IAuthRepository>() }
    singleOf(::UserRespositoryImpl) { bind <IUserRepository>() }
    singleOf(::ThesisRepositoryImpl) { bind<IThesisRepository>() }
    singleOf(::LockerRepositoryImpl) { bind<ILockerRepository>() }
}