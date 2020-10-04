package com.example.scoreapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scoreapp.data.model.Game
import com.example.scoreapp.domain.usecase.GetUpdatedViewSeason
import com.example.scoreapp.domain.usecase.UpdateGameUseCase
import com.example.scoreapp.ui.model.Season
/**
 * all parameters will be passed with koin
 */
class GameViewModel(
    val updateGameUseCase: UpdateGameUseCase,
    val getUpdatedViewSeason: GetUpdatedViewSeason
) : ViewModel() {
    /**
     * game and season to be show in the view
     */
    val game = MutableLiveData<Game>()
    val season = MutableLiveData<Season>()

    /**
     * tell if the view can show the toast
     */
    val showToast = MutableLiveData<Boolean>()

    suspend fun updateGame(updatedPoints: Int) {
        game.value?.let {
            if (updatedPoints == it.points) {
                showToast.postValue(false)
                return
            }
            it.points = updatedPoints
            /**
             * set updatedPoints to game
             */
            game.postValue(
                it.copy(
                    points = updatedPoints
                )
            )
            showToast.postValue(true)
            /**
             * update the game in database
             */
            updateGameUseCase.updateGame(it)
            updateSeason()
        }
    }

    /**
     * updates the max and min of the season if the game is one of them
     */
    suspend fun updateSeason() {
        season.value?.let {
            season.postValue(getUpdatedViewSeason.getUpdatedSeason(it.id))
        }
    }
}