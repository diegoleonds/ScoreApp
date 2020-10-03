package com.example.scoreapp.domain.usecase

import com.example.scoreapp.data.model.Game
import com.example.scoreapp.data.repository.GameRepositoryImpl
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class UpdateGameUseCaseTest {
    val repository = mockk<GameRepositoryImpl>()
    val useCase = UpdateGameUseCase(repository)

    @Test
    fun shouldRunOneTime() = runBlocking {
        coEvery { repository.updateGame(any()) } just runs
        useCase.updateGame(Game(
            id = 1,
            fkSeason = 1,
            points = 2
        ))
        coVerify (exactly = 1){ useCase.updateGame(any()) }
    }
}