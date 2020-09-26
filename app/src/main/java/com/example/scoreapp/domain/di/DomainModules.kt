package com.example.scoreapp.domain.di

import com.example.scoreapp.domain.usecase.GetViewSeasonsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetViewSeasonsUseCase() }
}