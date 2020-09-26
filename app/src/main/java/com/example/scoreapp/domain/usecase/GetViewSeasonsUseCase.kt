package com.example.scoreapp.domain.usecase

import com.example.scoreapp.data.model.Game
import com.example.scoreapp.domain.repository.GameRepository
import com.example.scoreapp.domain.repository.SeasonRepository
import com.example.scoreapp.domain.transform.SeasonTransform
import com.example.scoreapp.ui.model.Season as ViewSeason
import com.example.scoreapp.data.model.Season as ModelSeason

class GetViewSeasonsUseCase(
    val gameRepository: GameRepository,
    val seasonRepository: SeasonRepository
) {
    suspend fun getViewSeasons(): List<ViewSeason> {
        val transform = SeasonTransform()
        val modelSeasons = seasonRepository.getAll()
        val viewSeasons = ArrayList<ViewSeason>()
        var maxGame: Game
        var minGame: Game
        modelSeasons.forEach { season: ModelSeason ->
            maxGame = gameRepository.getSeasonGameWithMorePoints(season.id)
            minGame = gameRepository.getSeasonGameWithLessPoints(season.id)
            viewSeasons.add(
                transform.transform(
                    modelSeason = season,
                    maxGame = maxGame,
                    minGame = minGame
                )
            )
        }
        return viewSeasons
    }
}