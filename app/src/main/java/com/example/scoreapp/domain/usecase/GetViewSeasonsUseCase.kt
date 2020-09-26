package com.example.scoreapp.domain.usecase

import com.example.scoreapp.data.model.Game
import com.example.scoreapp.data.repository.GameRepositoryImpl
import com.example.scoreapp.data.repository.SeasonRepositoryImpl
import com.example.scoreapp.domain.repository.GameRepository
import com.example.scoreapp.domain.repository.SeasonRepository
import com.example.scoreapp.domain.transform.SeasonTransform
import org.koin.core.KoinComponent
import org.koin.core.inject
import com.example.scoreapp.ui.model.Season as ViewSeason
import com.example.scoreapp.data.model.Season as ModelSeason

class GetViewSeasonsUseCase() : KoinComponent {
    val seasonRepository: SeasonRepository by inject<SeasonRepositoryImpl>()
    val gameRepository: GameRepository by inject<GameRepositoryImpl>()

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