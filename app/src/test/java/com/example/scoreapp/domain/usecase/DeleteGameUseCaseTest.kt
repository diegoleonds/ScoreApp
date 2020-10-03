package com.example.scoreapp.domain.usecase

import com.example.scoreapp.data.model.Game
import com.example.scoreapp.data.repository.GameRepositoryImpl
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class DeleteGameUseCaseTest {
    val repository = mockk<GameRepositoryImpl>()
    val useCase = DeleteGameUseCase(repository)

    @Test
    fun testDelete() = runBlocking {
        coEvery { repository.deleteGame(any()) } just runs
        useCase.delete(Game(
            id = 1,
            points = 2,
            fkSeason = 1
        ))
        coVerify (exactly = 1){ repository.deleteGame(any()) }
    }
}