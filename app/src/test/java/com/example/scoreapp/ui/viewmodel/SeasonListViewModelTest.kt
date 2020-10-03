package com.example.scoreapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.scoreapp.domain.usecase.CreateSeasonUseCase
import com.example.scoreapp.domain.usecase.DeleteSeasonUseCase
import com.example.scoreapp.domain.usecase.GetViewSeasonsUseCase
import com.example.scoreapp.ui.model.Season
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module


class SeasonListViewModelTest {
    @Suppress("unused")
    @get:Rule
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    val getViewSeasonsUseCase = mockk<GetViewSeasonsUseCase>()
    val createSeasonUseCase = mockk<CreateSeasonUseCase>()
    val deleteSeasonUseCase = mockk<DeleteSeasonUseCase>()
    val viewModel = SeasonListViewModel(
        getViewsSeasonsUseCase = getViewSeasonsUseCase,
        createSeasonUseCase = createSeasonUseCase,
        delteSeasonUseCase = deleteSeasonUseCase
    )

    @Test
    fun shouldUpdateSeasonsList() = runBlocking {
        val expectedSeasons = listOf<Season>(
            Season(id = 1, maxRecord = 1, minRecord = 1),
            Season(id = 2, maxRecord = 2, minRecord = 2)
        )
        coEvery { getViewSeasonsUseCase.getViewSeasons() } returns expectedSeasons
        assertEquals(null, viewModel.seasonsList.value)
        viewModel.updateSeasonsList()
        assertEquals(expectedSeasons, viewModel.seasonsList.value)
    }

    @Test
    fun shouldReturnLastCreatedSeasonId() = runBlocking {
        val expectedId: Long = 1
        coEvery { createSeasonUseCase.createSeason() } returns expectedId
        viewModel.createSeason()

        assertEquals(expectedId, viewModel.seasonId.value)
    }

    @Test
    fun shouldDeleteSeason() = runBlocking {
        val season = Season(id = 1, maxRecord = 1, minRecord = 1)
        coEvery { deleteSeasonUseCase.deleteSeason(season) } just runs
        deleteSeasonUseCase.deleteSeason(season)
        coVerify (exactly = 1){ deleteSeasonUseCase.deleteSeason(season) }
    }
}