package com.example.scoreapp.domain.usecase

import com.example.scoreapp.data.repository.SeasonRepositoryImpl
import com.example.scoreapp.domain.transform.SeasonTransform
import com.example.scoreapp.ui.model.Season

class DeleteSeasonUseCase(
    val repository: SeasonRepositoryImpl
){
    val transform = SeasonTransform()

    /**
     * @param season from ui model
     */
    suspend fun deleteSeason(season: Season) {
        /**
         * transform the ui season into a model one and than call the delete method
         */
        repository.deleteSeason(transform.transformViewSeasonIntoModelSeason(season))
    }
}