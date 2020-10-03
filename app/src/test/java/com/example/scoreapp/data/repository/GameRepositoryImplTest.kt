package com.example.scoreapp.data.repository

import com.example.scoreapp.data.database.GameDAO
import com.example.scoreapp.data.model.Game
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotSame
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.math.exp

class GameRepositoryImplTest {
    val dao = mockk<GameDAO>()
    val repository = GameRepositoryImpl(dao)

    @Test
    fun shouldReturnGamesOfTheRightSeason() = runBlocking {
        val seasonOneGames = ArrayList<Game>()
        for (i in 0 until 10){
            seasonOneGames.add(
                Game(
                    id = i.toLong(),
                    fkSeason = 1,
                    points = i
                )
            )
        }
        val seasonTwoGames = ArrayList<Game>()
        for (i in 0 until 10){
            seasonTwoGames.add(
                Game(
                    id = i.toLong(),
                    fkSeason = 2,
                    points = i
                )
            )
        }
        coEvery { dao.getAllBySeason(1) } returns seasonOneGames
        coEvery { dao.getAllBySeason(2) } returns seasonTwoGames

        assertEquals(seasonOneGames, repository.getGamesBySeason(1))
        assertNotSame(seasonOneGames, repository.getGamesBySeason(2))

        assertEquals(seasonTwoGames, repository.getGamesBySeason(2))
        assertNotSame(seasonTwoGames, repository.getGamesBySeason(1))
    }

    @Test
    fun shouldReturnGameWithTheCurrentId() = runBlocking {
        val expectedGame = Game(
            id = 1,
            fkSeason = 1,
            points = 10
        )
        coEvery { dao.getById(1) } returns expectedGame
        assertEquals(expectedGame, repository.getById(1))
    }

    @Test
    fun shouldReturnSeasonGameWithMorePoints() = runBlocking {
        val expectedGame = Game(
            id = 1,
            fkSeason = 1,
            points = 100
        )
        coEvery { repository.getSeasonGameWithMorePoints(1) } returns expectedGame
        assertEquals(expectedGame, repository.getSeasonGameWithMorePoints(1))
    }

    @Test
    fun shouldReturnSeasonGameWithLessPoints() = runBlocking {
        val expectedGame = Game(
            id = 1,
            fkSeason = 1,
            points = 0
        )
        coEvery { repository.getSeasonGameWithLessPoints(1) } returns expectedGame
        assertEquals(expectedGame, repository.getSeasonGameWithLessPoints(1))
    }
}