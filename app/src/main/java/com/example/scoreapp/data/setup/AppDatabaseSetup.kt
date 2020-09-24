package com.example.scoreapp.data.setup

import android.content.Context
import androidx.room.Room
import com.example.scoreapp.data.database.AppDatabase
import com.example.scoreapp.data.database.GameDAO
import com.example.scoreapp.data.database.SeasonDAO

object AppDatabaseSetup {
    private lateinit var appDatabase: AppDatabase

    fun initDatabase(context: Context) {
        appDatabase = Room
            .databaseBuilder(context, AppDatabase::class.java, "app-db")
            .build()
    }

    fun initDatabaseInMemory(context: Context) {
        appDatabase = Room
            .inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    fun closeDatabase() = appDatabase.close()

    fun clearDatabase() {
        appDatabase.clearAllTables()
    }

    fun getGameDao(): GameDAO = appDatabase.gameDao()
    fun getSeasonDao(): SeasonDAO = appDatabase.seasonDao()
}