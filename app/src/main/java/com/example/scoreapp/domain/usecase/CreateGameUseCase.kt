package com.example.scoreapp.domain.usecase

import com.example.scoreapp.data.model.Game
import com.example.scoreapp.data.repository.GameRepositoryImpl
import com.example.scoreapp.domain.repository.GameRepository
import com.example.scoreapp.ui.model.Season

class CreateGameUseCase(
    val repository: GameRepositoryImpl
) {
    suspend fun createGame(points: Int, season: Season?) {
        /**
         * only create a game if the season is not null,
         * in the let block the season is referenced as it
         */
        season?.let {
            repository.insertGame(
                Game(
                    fkSeason = it.id,
                    points = points
                )
            )
        }
    }
}