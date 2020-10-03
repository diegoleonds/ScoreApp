package com.example.scoreapp.domain.repository

import com.example.scoreapp.data.model.Game

interface GameRepository {

    suspend fun getGamesBySeason(fkSeason: Long) : List<Game>
    suspend fun getById(id: Long) : Game
    suspend fun insertGame(game: Game)
    suspend fun deleteGame(game: Game)
    suspend fun updateGame(game: Game)
    suspend fun getSeasonGameWithMorePoints(fkSeason: Long) : Game?
    suspend fun getSeasonGameWithLessPoints(fkSeason: Long) : Game?
}