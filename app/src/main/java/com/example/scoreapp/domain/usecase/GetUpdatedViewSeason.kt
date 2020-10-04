package com.example.scoreapp.domain.usecase

import com.example.scoreapp.data.model.Game
import com.example.scoreapp.data.repository.GameRepositoryImpl
import com.example.scoreapp.ui.model.Season

class GetUpdatedViewSeason(
    val gameRepository: GameRepositoryImpl
) {
    /**
     * @param id is the season id you want to check for updates
     * @return ui season with the latest data
     */
    suspend fun getUpdatedSeason(id: Long): Season {
        val minGame: Game? = gameRepository.getSeasonGameWithLessPoints(id)
        val maxGame: Game? = gameRepository.getSeasonGameWithMorePoints(id)
        /**
         * if the games are null the season score will be set as zero
         */
        return Season(
            id = id,
            maxScore = maxGame?.points ?: 0,
            minScore = minGame?.points ?: 0
        )
    }
}