package com.example.scoreapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.scoreapp.data.model.Game
import com.example.scoreapp.domain.usecase.GetUpdatedViewSeason
import com.example.scoreapp.domain.usecase.UpdateGameUseCase
import com.example.scoreapp.ui.model.Season
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class GameViewModelTest {
    val updateGameUseCase = mockk<UpdateGameUseCase>()
    val getUpdatedViewSeason = mockk<GetUpdatedViewSeason>()
    val viewModel = GameViewModel(
        updateGameUseCase = updateGameUseCase,
        getUpdatedViewSeason = getUpdatedViewSeason
    )

    @Suppress("unused")
    @get:Rule
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun shouldUpdateSeasonValue() = runBlocking {
        val actualSeason = Season(
            id = 1,
            maxScore = 10,
            minScore = 2
        )
        val expectedSeason = actualSeason.copy(
            maxScore = 11,
            minScore = 3
        )
        coEvery { getUpdatedViewSeason.getUpdatedSeason(actualSeason.id) } returns
             expectedSeason

        viewModel.season.value = actualSeason
        assertNotEquals(expectedSeason, viewModel.season.value)

        viewModel.updateSeason()
        assertEquals(expectedSeason, viewModel.season.value)
    }

    @Test
    fun shouldUpdateGameValue() = runBlocking {
        val actualGame = Game(
            id = 1,
            fkSeason = 1,
            points = 10
        )
        val expectedGame = actualGame.copy(
            points = 2
        )
        coEvery { updateGameUseCase.updateGame(any()) } just runs

        viewModel.game.value = actualGame
        assertNotEquals(expectedGame, viewModel.game.value)

        viewModel.updateGame(2)
        assertEquals(expectedGame, viewModel.game.value)
    }

    @Test
    fun shouldUpdateShowToastValue() = runBlocking {
        val game = Game(
            id = 1,
            fkSeason = 1,
            points = 2
        )
        viewModel.game.value = game
        coEvery { updateGameUseCase.updateGame(any()) } just runs

        viewModel.updateGame(game.points)
        assertFalse(viewModel.showToast?.value ?: true)

        viewModel.updateGame(game.points + 1)
        assertTrue(viewModel.showToast?.value ?: false)
    }
}