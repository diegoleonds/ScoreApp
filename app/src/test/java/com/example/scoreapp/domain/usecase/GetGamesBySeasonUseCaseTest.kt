package com.example.scoreapp.domain.usecase

import com.example.scoreapp.data.model.Game
import com.example.scoreapp.data.model.Season
import com.example.scoreapp.data.repository.GameRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetGamesBySeasonUseCaseTest {
    val repository = mockk<GameRepositoryImpl>()
    val useCase = GetGamesBySeasonUseCase(repository)
    val season = Season(id = 1)

    @Test
    fun shouldReturnOnlyGamesOfCurrentSeason() = runBlocking {
        val expectedGames = ArrayList<Game>()
        for (i in 0 until 10){
            expectedGames.add(
                Game(
                    id = i.toLong(),
                    fkSeason = season.id,
                    points = i
                )
            )
        }
        coEvery { useCase.getGamesBySeason(season.id) } returns expectedGames
        assertEquals(expectedGames, useCase.getGamesBySeason(season.id))
    }
}