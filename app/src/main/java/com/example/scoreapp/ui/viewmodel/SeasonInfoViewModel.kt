package com.example.scoreapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scoreapp.data.model.Game
import com.example.scoreapp.domain.usecase.GetGamesBySeasonUseCase
/**
 * all parameters will be passed with koin
 */
class SeasonInfoViewModel(
    val getGamesBySeasonUseCase: GetGamesBySeasonUseCase
) : ViewModel() {
    /**
     * games of the season
     */
    val games = MutableLiveData<ArrayList<Game>>()

    suspend fun getSeasonGames(seasonId: Long) {
        games.postValue(ArrayList(getGamesBySeasonUseCase.getGamesBySeason(seasonId)))
    }

    fun getSeasonAverageScore(): Int {
        games.value?.let {
            if (it.size == 0) {
                return 0
            }
            var count = 0
            for (game: Game in it) {
                count += game.points
            }
            return count / it.size
        }
        return 0
    }

    /**
     * @return 0 if the games are null or there are less than two,
     * otherwise the number of times the min record was broken
     */
    fun getMinRecordFrequency(): Int {
        games.value?.let {
            if (it.size < 2) {
                return 0
            }
            var count = 0
            var game = it.get(0)
            for (i in 1 until it.size){
                if (game.points > it.get(i).points){
                    game = it.get(i)
                    count++
                }
            }
            return count
        }
        return 0
    }

    /**
     * @return 0 if the games are null or there are less than two,
     * otherwise the number of times the max record was broken
     */
    fun getMaxRecordFrequency(): Int {
        games.value?.let {
            if (it.size < 2) {
                return 0
            }
            var count = 0
            var game = it.get(0)
            for (i in 1 until it.size){
                if (game.points < it.get(i).points){
                    game = it.get(i)
                    count++
                }
            }
            return count
        }
        return 0
    }
}