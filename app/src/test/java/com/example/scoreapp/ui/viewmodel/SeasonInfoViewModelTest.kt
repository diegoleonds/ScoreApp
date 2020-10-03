package com.example.scoreapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.scoreapp.data.model.Game
import com.example.scoreapp.domain.usecase.GetGamesBySeasonUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class SeasonInfoViewModelTest {
    val getGamesBySeasonUseCase = mockk<GetGamesBySeasonUseCase>()
    val viewModel = SeasonInfoViewModel(getGamesBySeasonUseCase)

    @Suppress("unused")
    @get:Rule
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun getSeasonsTest() = runBlocking {
        val expectedGames = ArrayList<Game>()
        for (i in 0 until 10) {
            expectedGames.add(
                Game(
                    id = i.toLong(),
                    fkSeason = 1,
                    points = i
                )
            )
        }
        coEvery { getGamesBySeasonUseCase.getGamesBySeason(1) } returns expectedGames

        viewModel.getSeasonGames(1)
        assertEquals(expectedGames, viewModel.games.value)
    }

    @Test
    fun shouldReturnAverageScoreAsZeroWhenGameListIsNull() {
        viewModel.games.value = null
        assertEquals(0, viewModel.getSeasonAverageScore())
    }

    @Test
    fun shouldReturnAverageScoreAsZeroWhenGameListIsEmpty() {
        viewModel.games.value = ArrayList<Game>()
        assertEquals(0, viewModel.getSeasonAverageScore())
    }

    @Test
    fun getAverageScoreTest() {
        val games = ArrayList<Game>()
        for (i in 0 until 10) {
            games.add(
                Game(
                    id = i.toLong(),
                    fkSeason = 1,
                    points = 5
                )
            )
        }
        viewModel.games.value = games
        assertEquals(5, viewModel.getSeasonAverageScore())
    }

    @Test
    fun shouldReturnMinRecordFrequency() {
        val games = ArrayList<Game>(
            listOf(
                Game(
                    id = 1,
                    fkSeason = 1,
                    points = 11
                ),
                Game(
                    id = 2,
                    fkSeason = 1,
                    points = 12
                ),
                Game(
                    id = 3,
                    fkSeason = 1,
                    points = 8
                ),
                Game(
                    id = 4,
                    fkSeason = 1,
                    points = 1
                ),
                Game(
                    id = 5,
                    fkSeason = 1,
                    points = 5
                )
            )
        )
        val expectedMinRecordFrequency = 2
        viewModel.games.value = games
        assertEquals(expectedMinRecordFrequency, viewModel.getMinRecordFrequency())
    }

    @Test
    fun shouldReturnMaxRecordFrequency() {
        val games = ArrayList<Game>(
            listOf(
                Game(
                    id = 1,
                    fkSeason = 1,
                    points = 10
                ),
                Game(
                    id = 2,
                    fkSeason = 1,
                    points = 2
                ),
                Game(
                    id = 3,
                    fkSeason = 1,
                    points = 1
                ),
                Game(
                    id = 4,
                    fkSeason = 1,
                    points = 12
                )
            )
        )
        val expectedMaxRecordFrequency = 1
        viewModel.games.value = games
        assertEquals(expectedMaxRecordFrequency, viewModel.getMaxRecordFrequency())
    }
}