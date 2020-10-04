package com.example.scoreapp.domain.usecase

import com.example.scoreapp.data.model.Game
import com.example.scoreapp.data.repository.GameRepositoryImpl
import com.example.scoreapp.domain.repository.GameRepository

class UpdateGameUseCase(
    val repository: GameRepositoryImpl
) {
    suspend fun updateGame(game: Game) = repository.updateGame(game)
}