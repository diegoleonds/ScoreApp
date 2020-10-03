package com.example.scoreapp.domain.usecase

import com.example.scoreapp.data.model.Game
import com.example.scoreapp.domain.repository.GameRepository
import com.example.scoreapp.ui.model.Season

class CreateGameUseCase(
    val repository: GameRepository
) {
    suspend fun createGame(points: Int, season: Season?) {
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