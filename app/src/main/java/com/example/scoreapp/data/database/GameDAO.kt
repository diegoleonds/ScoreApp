package com.example.scoreapp.data.database

import androidx.room.*
import com.example.scoreapp.data.model.Game

@Dao
interface GameDAO : BaseDAO<Game> {

    @Query("SELECT * FROM game WHERE fkSeason = :fkSeason")
    suspend fun getAllBySeason(fkSeason: Long): List<Game>

    @Query("SELECT * FROM game WHERE id = :id")
    suspend fun getById(id: Long): Game
}