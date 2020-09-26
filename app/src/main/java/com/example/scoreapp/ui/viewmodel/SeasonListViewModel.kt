package com.example.scoreapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scoreapp.data.database.AppDatabase
import com.example.scoreapp.domain.usecase.GetViewSeasonsUseCase
import com.example.scoreapp.ui.model.Season
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SeasonListViewModel() : ViewModel() {
    val seasonsList: MutableLiveData<List<Season>?> = MutableLiveData()
    val useCase = GetViewSeasonsUseCase()

    fun updateSeasonsList() {
        CoroutineScope(Dispatchers.IO).launch {
            seasonsList.postValue(useCase.getViewSeasons())
        }
    }
}