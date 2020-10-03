package com.example.scoreapp.domain.usecase

import com.example.scoreapp.data.model.Game
import com.example.scoreapp.data.repository.GameRepositoryImpl
import com.example.scoreapp.ui.model.Season
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetUpdatedViewSeasonTest {
    val repository = mockk<GameRepositoryImpl>()
    val useCase = GetUpdatedViewSeason(repository)

    @Test
    fun shouldReturnUpdatedViewSeason() = runBlocking {
        val season = Season(
            id = 1,
            maxRecord = 10,
            minRecord = 2
        )
        val updatedGameWithMorePoints = Game(
            id = 1,
            fkSeason = season.id,
            points = 11
        )
        val updatedGameWithLessPoints = Game(
            id = 2,
            fkSeason = season.id,
            points = 1
        )
        coEvery { repository.getSeasonGameWithMorePoints(season.id) } returns
                updatedGameWithMorePoints

        coEvery { repository.getSeasonGameWithLessPoints(season.id) } returns
                updatedGameWithLessPoints

        val expectedSeason = Season(
            id = season.id,
            maxRecord = updatedGameWithMorePoints.points,
            minRecord = updatedGameWithLessPoints.points
        )
        assertEquals(expectedSeason, useCase.getUpdatedSeason(season.id))
    }

    @Test
    fun shouldReturnMaxAndMinScoreAsZeroWhenSeasonGamesAreNull() = runBlocking {
        val expectedSeason = Season(
            id = 1,
            maxRecord = 0,
            minRecord = 0
        )
        coEvery { repository.getSeasonGameWithMorePoints(expectedSeason.id) } returns
                null

        coEvery { repository.getSeasonGameWithLessPoints(expectedSeason.id) } returns
                null

        assertEquals(expectedSeason, useCase.getUpdatedSeason(expectedSeason.id))
    }
}