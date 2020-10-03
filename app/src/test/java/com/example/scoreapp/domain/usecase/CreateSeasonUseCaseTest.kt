package com.example.scoreapp.domain.usecase

import com.example.scoreapp.data.repository.SeasonRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class CreateSeasonUseCaseTest {
    val repository = mockk<SeasonRepositoryImpl>()
    val useCase = CreateSeasonUseCase(repository)

    @Test
    fun createTest() = runBlocking {
        val expectedId: Long = 1
        coEvery { repository.insertSeason(any()) } returns expectedId

        assertEquals(expectedId, useCase.createSeason())
    }
}