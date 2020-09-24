package com.example.scoreapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.scoreapp.data.model.Game
import com.example.scoreapp.data.model.Season

@Database(entities = arrayOf(Season::class, Game::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDAO
    abstract fun seasonDao(): SeasonDAO
}