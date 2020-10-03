package com.example.scoreapp.domain.usecase

import com.example.scoreapp.data.model.Game
import com.example.scoreapp.data.repository.GameRepositoryImpl
import com.example.scoreapp.domain.repository.GameRepository

class GetGamesBySeasonUseCase(
    val repository: GameRepositoryImpl
) {
    suspend fun getGamesBySeason(fkSeason: Long): List<Game> = repository.getGamesBySeason(fkSeason)
}