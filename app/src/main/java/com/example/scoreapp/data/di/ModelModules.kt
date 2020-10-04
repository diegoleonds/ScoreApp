package com.example.scoreapp.data.di

import androidx.room.Room
import com.example.scoreapp.data.database.AppDatabase
import com.example.scoreapp.data.repository.GameRepositoryImpl
import com.example.scoreapp.data.repository.SeasonRepositoryImpl
import org.koin.dsl.module

/**
 * @ file for dependency injection in this package classes
 */
val databaseModule = module {
    single {
        Room
            .databaseBuilder(get(), AppDatabase::class.java, "app-db")
            .build()
    }
}

val daoModule = module {
    single { get<AppDatabase>().gameDao() }
    single { get<AppDatabase>().seasonDao() }
}

val repositoryModule = module {
    factory { SeasonRepositoryImpl(get()) }
    factory { GameRepositoryImpl(get()) }
}

