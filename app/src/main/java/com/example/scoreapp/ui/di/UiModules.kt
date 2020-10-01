package com.example.scoreapp.ui.di

import android.content.Intent
import com.example.scoreapp.ui.activity.GameListActivity
import com.example.scoreapp.ui.model.Season
import com.example.scoreapp.ui.viewmodel.GameListViewModel
import com.example.scoreapp.ui.viewmodel.SeasonListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SeasonListViewModel() }
    viewModel { GameListViewModel(get(), get(), get()) }
}