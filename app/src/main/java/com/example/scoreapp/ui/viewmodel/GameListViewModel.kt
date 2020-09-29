package com.example.scoreapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scoreapp.domain.usecase.CreateGameUseCase
import com.example.scoreapp.ui.model.Season
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class GameListViewModel : ViewModel() {
    val createGameUseCase = CreateGameUseCase()
    val season: MutableLiveData<Season> = MutableLiveData()

    suspend fun addGame(points: Int) {
        createGameUseCase.createGame(points, season.value)
    }
}