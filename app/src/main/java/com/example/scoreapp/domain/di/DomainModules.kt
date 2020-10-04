package com.example.scoreapp.domain.di

import com.example.scoreapp.domain.usecase.*
import org.koin.dsl.module

/**
 * file for dependency injection of this package classes
 */
val useCaseModule = module {
    factory { GetViewSeasonsUseCase(get(), get()) }
    factory { CreateSeasonUseCase(get()) }
    factory { DeleteSeasonUseCase(get()) }
    factory { CreateGameUseCase(get()) }
    factory { GetGamesBySeasonUseCase(get()) }
    factory { DeleteGameUseCase(get()) }
    factory { UpdateGameUseCase(get()) }
    factory { GetUpdatedViewSeason(get()) }
}