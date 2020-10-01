package com.example.scoreapp.domain.usecase

import com.example.scoreapp.data.model.Game
import com.example.scoreapp.data.repository.GameRepositoryImpl
import com.example.scoreapp.domain.repository.GameRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class GetGamesBySeasonUseCase : KoinComponent{
    val repository: GameRepository by inject<GameRepositoryImpl>()

    suspend fun getGamesBySeason(fkSeason: Long): List<Game> = repository.getGamesBySeason(fkSeason)
}