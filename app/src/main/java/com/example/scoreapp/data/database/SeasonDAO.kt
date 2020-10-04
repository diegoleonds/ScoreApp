package com.example.scoreapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.scoreapp.data.model.Season

@Dao
interface SeasonDAO : BaseDAO<Season> {

    @Query("SELECT * FROM season")
    suspend fun getAll() : List<Season>

    /**
     * @return the id of the inserted season
     */
    @Insert
    suspend fun insert(season: Season): Long
}