package com.example.scoreapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

/**
 * Base interface implemented in the others DAO's
 */
@Dao
interface BaseDAO<T> {

    @Delete
    suspend fun delete(entity: T)

    @Update
    suspend fun update(entity: T)
}