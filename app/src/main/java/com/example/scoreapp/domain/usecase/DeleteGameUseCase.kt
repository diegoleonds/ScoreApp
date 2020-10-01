package com.example.scoreapp.domain.usecase

import com.example.scoreapp.data.model.Game
import com.example.scoreapp.data.repository.GameRepositoryImpl
import org.koin.core.KoinComponent
import org.koin.core.inject

class DeleteGameUseCase : KoinComponent {
    val repository by inject<GameRepositoryImpl>()

    suspend fun delete(game: Game){
        repository.deleteGame(game)
     }
}