package com.example.scoreapp.domain.usecase

import com.example.scoreapp.data.model.Game
import com.example.scoreapp.domain.repository.GameRepository

class DeleteGameUseCase(
    val repository: GameRepository
){
    suspend fun delete(game: Game){
        repository.deleteGame(game)
     }
}