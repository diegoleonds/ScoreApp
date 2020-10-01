package com.example.scoreapp.domain.usecase

import com.example.scoreapp.data.model.Game
import com.example.scoreapp.data.repository.GameRepositoryImpl
import com.example.scoreapp.data.repository.SeasonRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import com.example.scoreapp.data.model.Season as ModelSeason
import com.example.scoreapp.ui.model.Season as ViewSeason

class GetViewSeasonsUseCaseTest {

    val seasonRepository = mockk<SeasonRepositoryImpl>()
    val gameRepository = mockk<GameRepositoryImpl>()
    val useCase = GetViewSeasonsUseCase()

    @Before
    fun init(){
        startKoin { modules(module {
            single { seasonRepository }
            single { gameRepository }
        }) }
    }

    @After
    fun stopKoinAfterTest() = stopKoin()

    @Test
    fun shouldReturnViewSeasonWithMaxAndMinRecords() = runBlocking {
        val modelSeasons = listOf(ModelSeason(1), ModelSeason(2))
        coEvery { seasonRepository.getAll() } returns modelSeasons

        val seasonOneMaxGame = Game(
            id = 1,
            fkSeason = 1,
            points = 10
        )
        val seasonOneMinGame = Game(
            id = 2,
            fkSeason = 1,
            points = 2
        )

        val seasonTwoMaxGame = Game(
            id = 3,
            fkSeason = 2,
            points = 10
        )
        val seasonTwoMinGame = Game(
            id = 4,
            fkSeason = 1,
            points = 1
        )

        coEvery { gameRepository.getSeasonGameWithMorePoints(1) } returns seasonOneMaxGame
        coEvery { gameRepository.getSeasonGameWithLessPoints(1) } returns seasonOneMinGame

        coEvery { gameRepository.getSeasonGameWithMorePoints(2) } returns seasonTwoMaxGame
        coEvery { gameRepository.getSeasonGameWithLessPoints(2) } returns seasonTwoMinGame

        val expectedViewSeasonOne = ViewSeason(
            id = 1,
            maxRecord = seasonOneMaxGame.points,
            minRecord = seasonOneMinGame.points
        )
        val expectedViewSeasonTwo = ViewSeason(
            id = 2,
            maxRecord = seasonTwoMaxGame.points,
            minRecord = seasonTwoMinGame.points
        )
        val expectedViewSeasons = listOf(expectedViewSeasonOne, expectedViewSeasonTwo)

        assertEquals(expectedViewSeasons, useCase.getViewSeasons())
    }
}