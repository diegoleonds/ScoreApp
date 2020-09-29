package com.example.scoreapp.domain.usecase

import com.example.scoreapp.data.repository.SeasonRepositoryImpl
import com.example.scoreapp.ui.model.Season
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

class CreateSeasonUseCaseTest {

    val repository = mockk<SeasonRepositoryImpl>()
    val useCase = CreateSeasonUseCase()

    @Before
    fun init(){
        startKoin {
            modules(
                module {
                    single { repository }
                }
            )
        }
    }

    @After
    fun stopKoinAfterTest() = stopKoin()

    @Test
    fun createTest() = runBlocking {
        val expectedId: Long = 1
        coEvery { repository.insertSeason(any()) } returns expectedId

        assertEquals(expectedId, useCase.createSeason())
    }
}