package com.example.scoreapp.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.scoreapp.data.database.AppDatabase
import com.example.scoreapp.data.database.GameDAO
import com.example.scoreapp.data.model.Game
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class GameDAOTest {
    private lateinit var dao: GameDAO
    private lateinit var db: AppDatabase

    @Before
    fun init() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()

        dao = db.gameDao()
    }

    @After
    fun finish() {
        db.close()
    }

    @Test
    fun insertGameAndReadIt() = runBlocking {
        val expectedGame = Game(
            1,
            fkSeason = 0,
            points = 12
        )
        dao.insert(expectedGame)
        val game = dao.getById(1)

        assertEquals(expectedGame, game)
    }

    @Test
    fun getGamesBySeason() = runBlocking {

        val expectedGames = ArrayList<Game>()
        var game: Game

        for (i in 1..10) {
            game = Game(
                id = i.toLong(),
                fkSeason = 1,
                points = 1
            )
            expectedGames.add(game)
            dao.insert(game)
        }

        val games = dao.getAllBySeason(1)
        assertEquals(expectedGames, games)
    }

    @Test
    fun shouldReturnSeasonGameWithMorePoints() = runBlocking {
        val expectedGame = Game(
            id = 1,
            fkSeason = 1,
            points = 100
        )
        dao.insert(expectedGame)

        for (i in 2..10) {
            dao.insert(Game(
                id = i.toLong(),
                fkSeason = 1,
                points = 100
            ))
        }
        val game = dao.getSeasonGameWithMorePoints(1)
        assertEquals(expectedGame, game)
    }

    @Test
    fun shouldReturnSeasonGameWithLessPoints() = runBlocking {
        val expectedGame = Game(
            id = 1,
            fkSeason = 1,
            points = 99
        )
        dao.insert(expectedGame)

        for (i in 2..10) {
            dao.insert(Game(
                id = i.toLong(),
                fkSeason = 1,
                points = 100
            ))
        }
        val game = dao.getSeasonGameWithLessPoints(1)
        assertEquals(expectedGame, game)
    }


}