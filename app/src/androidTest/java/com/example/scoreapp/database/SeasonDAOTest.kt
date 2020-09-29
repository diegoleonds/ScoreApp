package com.example.scoreapp.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.scoreapp.data.database.AppDatabase
import com.example.scoreapp.data.database.SeasonDAO
import com.example.scoreapp.data.model.Season
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SeasonDAOTest {
    private lateinit var dao: SeasonDAO
    private lateinit var db: AppDatabase

    @Before
    fun init(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()

        dao = db.seasonDao()
    }

    @After
    fun finish(){
        db.close()
    }

    @Test
    fun insertSeasonsAndGetThem() = runBlocking{
        val expectedSeasons = ArrayList<Season>()
        var season: Season

        for(i in 1..10){
            season = Season(i.toLong())
            expectedSeasons.add(season)
            dao.insert(season)
        }

        val seasons = dao.getAll()
        assertEquals(expectedSeasons, seasons)
    }

    @Test
    fun checkingIfInsertIsReturningTheIdInserted() = runBlocking {
        val expectedId: Long = 1
        val insertedId = dao.insert(Season(expectedId))

        assertEquals(expectedId, insertedId)
    }
}