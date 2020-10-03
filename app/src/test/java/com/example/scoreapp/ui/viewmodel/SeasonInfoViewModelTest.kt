package com.example.scoreapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.scoreapp.data.model.Game
import com.example.scoreapp.domain.usecase.GetGamesBySeasonUseCase
import io.mockk.mockk
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

class SeasonInfoViewModelTest {
    val getGamesBySeasonUseCase = mockk<GetGamesBySeasonUseCase>()
    val viewModel = SeasonInfoViewModel(getGamesBySeasonUseCase)

    @Suppress("unused")
    @get:Rule
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun shouldReturnMinRecordFrequency(){
        val games = ArrayList<Game>(
            listOf(
                Game(
                    id = 1,
                    fkSeason = 1,
                    points = 11
                ),
                Game(
                    id = 2,
                    fkSeason = 1,
                    points = 12
                ),
                Game(
                    id = 3,
                    fkSeason = 1,
                    points = 8
                ),
                Game(
                    id = 4,
                    fkSeason = 1,
                    points = 1
                ),
                Game(
                    id = 5,
                    fkSeason = 1,
                    points = 5
                )
            ))
        val expectedMinRecordFrequency = 2
        viewModel.games.value = games
        assertEquals(expectedMinRecordFrequency, viewModel.getMinRecordFrequency())
    }

    @Test
    fun shouldReturnMaxRecordFrequency(){
        val games = ArrayList<Game>(
            listOf(
                Game(
                    id = 1,
                    fkSeason = 1,
                    points = 10
                ),
                Game(
                    id = 2,
                    fkSeason = 1,
                    points = 2
                ),
                Game(
                    id = 3,
                    fkSeason = 1,
                    points = 1
                ),
                Game(
                    id = 4,
                    fkSeason = 1,
                    points = 12
                )
            ))
        val expectedMaxRecordFrequency = 1
        viewModel.games.value = games
        assertEquals(expectedMaxRecordFrequency, viewModel.getMaxRecordFrequency())
    }
}