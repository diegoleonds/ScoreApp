package com.example.scoreapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scoreapp.data.model.Game
import com.example.scoreapp.domain.usecase.CreateGameUseCase
import com.example.scoreapp.domain.usecase.GetGamesBySeasonUseCase
import com.example.scoreapp.ui.model.Season

class GameListViewModel : ViewModel() {
    val createGameUseCase = CreateGameUseCase()
    val getGamesUseCase = GetGamesBySeasonUseCase()
    val season: MutableLiveData<Season> = MutableLiveData()
    val games: MutableLiveData<ArrayList<Game>> = MutableLiveData()

    suspend fun updateGamesList() {
        season.value?.let {
            games.postValue(ArrayList(getGamesUseCase.getGamesBySeason(it.id)))
            updateGamesOfCurrentSeason()
        }
    }

    suspend fun addGame(points: Int) {
        createGameUseCase.createGame(points, season.value)
        updateGamesList()
    }

    fun getGameWithMaxRecord(): Int? {
        games.value?.let {
            if (it.size < 2){
                return null
            }
            var gamePosition = 0
            for (i in 1 until it.size) {
                if (it.get(i).points > it.get(gamePosition).points)
                    gamePosition = i
            }
            return gamePosition
        }
        return null
    }

    fun getGameWithMinRecord(): Int? {
        games.value?.let {
            if (it.size < 2){
                return null
            }
            var gamePosition = 0
            for (i in 1 until it.size) {
                if (it.get(i).points < it.get(gamePosition).points)
                    gamePosition = i
            }
            return gamePosition
        }
        return null
    }

    fun updateGamesOfCurrentSeason() {
        games.value?.let {
            season.value?.run {
                for (game: Game in it) {
                    if (game.points > this.maxRecord) {
                        this.maxRecord = game.points
                    } else if (game.points < this.minRecord) {
                        this.minRecord = game.points
                    }
                }
            }
        }
    }
}