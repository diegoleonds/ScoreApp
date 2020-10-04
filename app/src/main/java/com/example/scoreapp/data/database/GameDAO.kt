package com.example.scoreapp.data.database

import androidx.room.*
import com.example.scoreapp.data.model.Game

/**
 * Contain the methods for data access
 */
@Dao
interface GameDAO : BaseDAO<Game> {

    @Insert
    suspend fun insert(game: Game)

    @Query("SELECT * FROM game WHERE fkSeason = :fkSeason")
    suspend fun getAllBySeason(fkSeason: Long): List<Game>

    @Query("SELECT * FROM game WHERE id = :id")
    suspend fun getById(id: Long): Game

    @Query("SELECT * FROM game WHERE points = (SELECT MAX(points) FROM game WHERE fkSeason = :fkSeason)")
    suspend fun getSeasonGameWithMorePoints(fkSeason: Long) : Game

    @Query("SELECT * FROM game WHERE points = (SELECT MIN(points) FROM game WHERE fkSeason = :fkSeason)")
    suspend fun getSeasonGameWithLessPoints(fkSeason: Long) : Game
}