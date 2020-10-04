package com.example.scoreapp.ui.di

import android.content.Intent
import com.example.scoreapp.domain.usecase.UpdateGameUseCase
import com.example.scoreapp.ui.activity.GameListActivity
import com.example.scoreapp.ui.model.Season
import com.example.scoreapp.ui.viewmodel.GameListViewModel
import com.example.scoreapp.ui.viewmodel.GameViewModel
import com.example.scoreapp.ui.viewmodel.SeasonInfoViewModel
import com.example.scoreapp.ui.viewmodel.SeasonListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * file for dependency injection of this package classes
 */
val viewModelModule = module {
    viewModel { SeasonListViewModel(get(), get(), get()) }
    viewModel { GameListViewModel(get(), get(), get()) }
    viewModel { SeasonInfoViewModel(get()) }
    viewModel { GameViewModel(get(), get()) }
}