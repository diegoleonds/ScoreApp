package com.example.scoreapp.domain.usecase

import com.example.scoreapp.data.model.Game
import com.example.scoreapp.data.repository.GameRepositoryImpl
import com.example.scoreapp.domain.repository.GameRepository
import com.example.scoreapp.ui.model.Season
import org.koin.core.KoinComponent
import org.koin.core.inject

class CreateGameUseCase : KoinComponent {
    val repository: GameRepository by inject<GameRepositoryImpl>()

    suspend fun createGame(points: Int, season: Season?) {
        season?.let {
            repository.insertGame(
                Game(
                    fkSeason = it.id,
                    points = points,
                    maxRecord = (points > it.maxRecord),
                    minRecord = (points < it.minRecord)
                )
            )
        }
    }
}