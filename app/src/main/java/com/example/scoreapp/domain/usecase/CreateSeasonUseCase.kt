package com.example.scoreapp.domain.usecase

import com.example.scoreapp.data.model.Season
import com.example.scoreapp.data.repository.GameRepositoryImpl
import com.example.scoreapp.data.repository.SeasonRepositoryImpl
import com.example.scoreapp.domain.repository.GameRepository
import com.example.scoreapp.domain.repository.SeasonRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class CreateSeasonUseCase: KoinComponent {
    val seasonRepository: SeasonRepository by inject<SeasonRepositoryImpl>()

    suspend fun createSeason(): Long = seasonRepository.insertSeason(Season())

}