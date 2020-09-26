package com.example.scoreapp.data.repository

import com.example.scoreapp.data.database.GameDAO
import com.example.scoreapp.data.model.Game
import com.example.scoreapp.domain.repository.GameRepository

class GameRepositoryImpl(
    val dao: GameDAO
) : GameRepository {
    override suspend fun getGamesBySeason(fkSeason: Long): List<Game> = dao.getAllBySeason(fkSeason)

    override suspend fun getById(id: Long): Game = dao.getById(id)

    override suspend fun insertGame(game: Game) = dao.insert(game)

    override suspend fun deleteGame(game: Game) = dao.delete(game)

    override suspend fun updateGame(game: Game) = dao.update(game)

    override suspend fun getSeasonGameWithMorePoints(fkSeason: Long): Game =
        dao.getSeasonGameWithMorePoints(fkSeason)

    override suspend fun getSeasonGameWithLessPoints(fkSeason: Long): Game =
        dao.getSeasonGameWithLessPoints(fkSeason)
}