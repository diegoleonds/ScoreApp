package com.example.scoreapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.scoreapp.data.model.Game
import com.example.scoreapp.domain.usecase.CreateGameUseCase
import com.example.scoreapp.domain.usecase.DeleteGameUseCase
import com.example.scoreapp.domain.usecase.GetGamesBySeasonUseCase
import com.example.scoreapp.ui.model.Season
import io.mockk.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GameListViewModelTest {

    @Suppress("unused")
    @get:Rule
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    val createGameUseCase = mockk<CreateGameUseCase>()
    val getGamesUseCase = mockk<GetGamesBySeasonUseCase>()
    val deleteGameUseCase = mockk<DeleteGameUseCase>()
    val viewModel = GameListViewModel(
        createGameUseCase = createGameUseCase,
        getGamesUseCase = getGamesUseCase,
        deleteGameUseCase = deleteGameUseCase
    )

    val season = Season(
        id = 1,
        maxRecord = 10,
        minRecord = 6
    )

    val gameWithLessPoints = Game(
        id = 1,
        fkSeason = season.id,
        points = 2
    )

    val game = Game(
        id = 2,
        fkSeason = season.id,
        points = 6
    )

    val gameWithMaxPoints = Game(
        id = 3,
        fkSeason = season.id,
        points = 8
    )

    val expectedGames = ArrayList<Game>(
        listOf(
            gameWithLessPoints,
            game,
            gameWithMaxPoints
        )
    )

    @Before
    fun init() {
        viewModel.season.value = season
    }

    @Test
    fun shouldUpdateListWithNewGames() = runBlocking {
        coEvery { getGamesUseCase.getGamesBySeason(season.id) } returns expectedGames
        viewModel.updateGamesList()
        assertEquals(expectedGames, viewModel.games.value)

        expectedGames.remove(gameWithLessPoints)
        viewModel.updateGamesList()
        assertEquals(expectedGames, viewModel.games.value)

        expectedGames.add(
            Game(
                id = 4,
                fkSeason = season.id,
                points = 11
            )
        )

        viewModel.updateGamesList()
        assertEquals(expectedGames, viewModel.games.value)
    }

    @Test
    fun shouldUpdateGameListAfterDelete() = runBlocking {
        expectedGames.remove(gameWithLessPoints)
        coEvery { getGamesUseCase.getGamesBySeason(any()) } returns expectedGames
        coEvery { deleteGameUseCase.delete(any()) } just runs
        viewModel.deleteGame(
            gameWithLessPoints
        )
        coVerify(exactly = 1) { deleteGameUseCase.delete(any()) }
        assertEquals(expectedGames, viewModel.games.value)
    }

    @Test
    fun shouldUpdateGameListAfterInsert() = runBlocking {
        expectedGames.add(
            Game(
                id = 4,
                fkSeason = 1,
                points = 12
            )
        )
        coEvery { getGamesUseCase.getGamesBySeason(any()) } returns expectedGames
        coEvery { createGameUseCase.createGame(any(), any()) } just runs
        viewModel.addGame(12)
        coVerify(exactly = 1) { createGameUseCase.createGame(any(), any()) }
        assertEquals(expectedGames, viewModel.games.value)
    }

    @Test
    fun shouldGetMaxRecordGame(){
        viewModel.games.value = expectedGames
        assertEquals(2, viewModel.getPositionOfGameWithMaxRecord())
    }

    @Test
    fun shouldGetMinRecordGame(){
        viewModel.games.value = expectedGames
        assertEquals(0, viewModel.getPositionOfGameWithMinRecord())
    }

    @Test
    fun shouldUpdateMaxAndMinGamesOfSeason(){
        viewModel.season.value = season
        val expectedMaxPoints = gameWithMaxPoints.points + 1
        val expectedMinPoints = gameWithLessPoints.points - 1
        expectedGames.add(
            Game(
                id = 4,
                fkSeason = season.id,
                points = expectedMaxPoints
            )
        )
        expectedGames.add(
            Game(
                id = 5,
                fkSeason = season.id,
                points = expectedMinPoints
            )
        )
        viewModel.games.value = expectedGames
        viewModel.updateGamesOfCurrentSeason()
        val viewModelSeason = viewModel.season.value
        assertEquals(expectedMaxPoints, viewModelSeason?.maxRecord)
        assertEquals(expectedMinPoints, viewModelSeason?.minRecord)
    }
}