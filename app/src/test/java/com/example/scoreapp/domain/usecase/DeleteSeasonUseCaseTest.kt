package com.example.scoreapp.domain.usecase

import com.example.scoreapp.data.repository.SeasonRepositoryImpl
import com.example.scoreapp.ui.model.Season
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

class DeleteSeasonUseCaseTest {

    val repository = mockk<SeasonRepositoryImpl>()
    val useCase = DeleteSeasonUseCase()

    @Before
    fun init() {
        startKoin {
            modules(module {
                single { repository }
            })
        }
    }

    @After
    fun stopKoinAfterTest() = stopKoin()

    @Test
    fun deleteTest() = runBlocking {
        val season = Season(
            id = 1,
            maxRecord = 10,
            minRecord = 2)

        coEvery { repository.deleteSeason(any()) } just runs
        useCase.deleteSeason(season)
        coVerify(exactly = 1) { repository.deleteSeason(any()) }
    }
}