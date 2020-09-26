package com.example.scoreapp.data.setup

import com.example.scoreapp.data.database.AppDatabase
import com.example.scoreapp.data.database.GameDAO
import com.example.scoreapp.data.database.SeasonDAO
import org.koin.core.KoinComponent
import org.koin.core.inject

object AppDatabaseSetup : KoinComponent {
    private val appDatabase: AppDatabase by inject()

    fun closeDatabase() = appDatabase.close()

    fun clearDatabase() {
        appDatabase.clearAllTables()
    }

    fun getGameDao(): GameDAO = appDatabase.gameDao()
    fun getSeasonDao(): SeasonDAO = appDatabase.seasonDao()
}