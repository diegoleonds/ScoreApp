package com.example.scoreapp.domain.di

import com.example.scoreapp.domain.usecase.*
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetViewSeasonsUseCase() }
    factory { CreateSeasonUseCase() }
    factory { DeleteSeasonUseCase() }
    factory { CreateGameUseCase() }
    factory { GetGamesBySeasonUseCase() }
    factory { DeleteGameUseCase() }
}