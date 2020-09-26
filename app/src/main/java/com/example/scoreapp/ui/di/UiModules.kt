package com.example.scoreapp.ui.di

import com.example.scoreapp.ui.viewmodel.SeasonListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SeasonListViewModel() }
}