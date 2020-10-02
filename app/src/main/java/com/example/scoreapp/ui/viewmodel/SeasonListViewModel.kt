package com.example.scoreapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scoreapp.domain.usecase.CreateSeasonUseCase
import com.example.scoreapp.domain.usecase.DeleteSeasonUseCase
import com.example.scoreapp.domain.usecase.GetViewSeasonsUseCase
import com.example.scoreapp.ui.model.Season
import org.koin.core.KoinComponent
import org.koin.core.inject


class SeasonListViewModel(
    val getViewsSeasonsUseCase: GetViewSeasonsUseCase,
    val createSeasonUseCase: CreateSeasonUseCase,
    val delteSeasonUseCase: DeleteSeasonUseCase
) : ViewModel() {
    val seasonsList: MutableLiveData<List<Season>?> = MutableLiveData()
    val seasonId: MutableLiveData<Long> = MutableLiveData()

    suspend fun updateSeasonsList() {
        seasonsList.postValue(getViewsSeasonsUseCase.getViewSeasons())
    }

    suspend fun createSeason() {
        seasonId.postValue(createSeasonUseCase.createSeason())
    }

    suspend fun deleteSeason(season: Season) {
        delteSeasonUseCase.deleteSeason(season)
    }
}