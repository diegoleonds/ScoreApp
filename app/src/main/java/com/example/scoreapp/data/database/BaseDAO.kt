package com.example.scoreapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

@Dao
interface BaseDAO<T> {

    @Insert
    suspend fun insert(entity: T)

    @Delete
    suspend fun delete(entity: T)

    @Update
    suspend fun update(entity: T)
}