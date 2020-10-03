package com.example.scoreapp.domain.usecase

import com.example.scoreapp.data.repository.GameRepositoryImpl
import com.example.scoreapp.ui.model.Season
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Test

class CreateGameUseCaseTest {
    val repository = mockk<GameRepositoryImpl>()
    val season = Season(
        id = 1,
        maxRecord = 10,
        minRecord = 2
    )

    val useCase = CreateGameUseCase(repository)

    @Test
    fun shouldCreateGameWithMaxRecord() = runBlocking {
        coEvery { repository.insertGame(any()) } just runs
        useCase.createGame(11, season)
        coVerify(exactly = 1) { repository.insertGame(any()) }
    }
}