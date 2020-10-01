package com.example.scoreapp.domain.usecase

import com.example.scoreapp.data.repository.GameRepositoryImpl
import com.example.scoreapp.ui.model.Season
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

class CreateGameUseCaseTest {
    val repository = mockk<GameRepositoryImpl>()
    val season = Season(
            id = 1,
            maxRecord = 10,
            minRecord = 2
        )

    val useCase = CreateGameUseCase()

    @Before
    fun init() {
        startKoin {
            modules(module {
                factory { repository }
            })
        }
    }

    @After
    fun stopKoinAfterTest() {
        stopKoin()
    }

    @Test
    fun shouldCreateGameWithMaxRecord() = runBlocking {
        coEvery { repository.insertGame(any()) } just runs
        useCase.createGame(11, season)
        coVerify (exactly = 1){ repository.insertGame(any()) }
    }
}