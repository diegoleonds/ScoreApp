package com.example.scoreapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scoreapp.domain.usecase.CreateSeasonUseCase
import com.example.scoreapp.domain.usecase.DeleteSeasonUseCase
import com.example.scoreapp.domain.usecase.GetViewSeasonsUseCase
import com.example.scoreapp.ui.model.Season
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * all parameters will be passed with koin
 */
class SeasonListViewModel(
    val getViewsSeasonsUseCase: GetViewSeasonsUseCase,
    val createSeasonUseCase: CreateSeasonUseCase,
    val delteSeasonUseCase: DeleteSeasonUseCase
) : ViewModel() {
    /**
     * season list, will be updated with the database
     */
    val seasonsList: MutableLiveData<List<Season>?> = MutableLiveData()

    /**
     * id of the latest inserted season
     */
    val seasonId: MutableLiveData<Long> = MutableLiveData()

    /**
     * get all seasons from database and set them to seasonList
     */
    suspend fun updateSeasonsList() {
        seasonsList.postValue(getViewsSeasonsUseCase.getViewSeasons())
    }

    /**
     * create a season in the database and set it id to seasonId
     */
    suspend fun createSeason() {
        seasonId.postValue(createSeasonUseCase.createSeason())
    }

    suspend fun deleteSeason(season: Season) {
        delteSeasonUseCase.deleteSeason(season)
    }
}