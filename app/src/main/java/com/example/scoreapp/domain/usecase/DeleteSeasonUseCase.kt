package com.example.scoreapp.domain.usecase

import com.example.scoreapp.data.repository.SeasonRepositoryImpl
import com.example.scoreapp.domain.repository.SeasonRepository
import com.example.scoreapp.domain.transform.SeasonTransform
import com.example.scoreapp.ui.model.Season

class DeleteSeasonUseCase(
    val repository: SeasonRepositoryImpl
){
    val transform = SeasonTransform()

    suspend fun deleteSeason(season: Season) {
        repository.deleteSeason(transform.transformViewSeasonIntoModelSeason(season))
    }
}