package com.example.scoreapp.domain.usecase

import com.example.scoreapp.data.repository.SeasonRepositoryImpl
import com.example.scoreapp.ui.model.Season
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Test

class DeleteSeasonUseCaseTest {
    val repository = mockk<SeasonRepositoryImpl>()
    val useCase = DeleteSeasonUseCase(repository)

    @Test
    fun deleteTest() = runBlocking {
        val season = Season(
            id = 1,
            maxScore = 10,
            minScore = 2
        )

        coEvery { repository.deleteSeason(any()) } just runs
        useCase.deleteSeason(season)
        coVerify(exactly = 1) { repository.deleteSeason(any()) }
    }
}