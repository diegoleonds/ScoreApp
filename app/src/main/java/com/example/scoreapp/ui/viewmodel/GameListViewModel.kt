package com.example.scoreapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scoreapp.data.model.Game
import com.example.scoreapp.domain.usecase.CreateGameUseCase
import com.example.scoreapp.domain.usecase.DeleteGameUseCase
import com.example.scoreapp.domain.usecase.GetGamesBySeasonUseCase
import com.example.scoreapp.ui.model.Season
/**
 * all parameters will be passed with koin
 */
class GameListViewModel(
    val createGameUseCase: CreateGameUseCase,
    val getGamesUseCase: GetGamesBySeasonUseCase,
    val deleteGameUseCase: DeleteGameUseCase
) : ViewModel() {
    /**
     * season of the games in the list
     */
    val season: MutableLiveData<Season> = MutableLiveData()

    /**
     * games of the season
     */
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

    /**
     * @return null if the games are null or there are less than two,
     * otherwise the position of the game with max record
     */
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

    /**
     * @return null if the games are null or there are less than two,
     * otherwise the position of the game with min record
     */
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

    fun setMinAndMaxSeasonScoreGames() {
        /**
         * will enter the blocks only if the values are not null
         */
        games.value?.let {
            season.value?.run {
                /**
                 * in this block games.value is referenced as it,
                 * and season.value is referenced as this
                 */
                if (it.size == 0) {
                    this.minScore = 0
                    this.maxScore = 0
                    return
                }
                var maxPointsGame: Int = it.get(0).points
                var minPointsGame: Int = it.get(0).points
                /**
                 * search for the games
                 */
                for (i in 1 until it.size){
                    if (maxPointsGame < it.get(i).points){
                        maxPointsGame = it.get(i).points
                    } else if(minPointsGame > it.get(i).points){
                        minPointsGame = it.get(i).points
                    }
                }
                this.maxScore = maxPointsGame
                this.minScore = minPointsGame
            }
        }
    }
}