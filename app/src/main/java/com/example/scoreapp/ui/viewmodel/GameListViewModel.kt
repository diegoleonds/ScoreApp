package com.example.scoreapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scoreapp.data.model.Game
import com.example.scoreapp.domain.usecase.CreateGameUseCase
import com.example.scoreapp.domain.usecase.DeleteGameUseCase
import com.example.scoreapp.domain.usecase.GetGamesBySeasonUseCase
import com.example.scoreapp.ui.model.Season

class GameListViewModel(
    val createGameUseCase: CreateGameUseCase,
    val getGamesUseCase: GetGamesBySeasonUseCase,
    val deleteGameUseCase: DeleteGameUseCase
) : ViewModel() {
    val season: MutableLiveData<Season> = MutableLiveData()
    val games: MutableLiveData<ArrayList<Game>> = MutableLiveData()

    suspend fun updateGamesList() {
        season.value?.let {
            games.postValue(ArrayList(getGamesUseCase.getGamesBySeason(it.id)))
        }
    }

    suspend fun addGame(points: Int) {
        createGameUseCase.createGame(points, season.value)
        updateGamesList()
    }

    suspend fun deleteGame(game: Game) {
        deleteGameUseCase.delete(game)
        updateGamesList()
    }

    fun getPositionOfGameWithMaxRecord(): Int? {
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

    fun getPositionOfGameWithMinRecord(): Int? {
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
                if (it.size == 0) {
                    return
                }
                var maxPointsGame: Int = it.get(0).points
                var minPointsGame: Int = it.get(0).points
                for (i in 1 until it.size){
                    if (maxPointsGame < it.get(i).points){
                        maxPointsGame = it.get(i).points
                    } else if(minPointsGame > it.get(i).points){
                        minPointsGame = it.get(i).points
                    }
                }
                this.maxRecord = maxPointsGame
                this.minRecord = minPointsGame
            }
        }
    }
}