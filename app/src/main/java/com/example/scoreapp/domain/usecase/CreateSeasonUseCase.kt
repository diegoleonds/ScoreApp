package com.example.scoreapp.domain.usecase

import com.example.scoreapp.data.model.Season
import com.example.scoreapp.data.repository.SeasonRepositoryImpl
import com.example.scoreapp.domain.repository.SeasonRepository

class CreateSeasonUseCase(
    val seasonRepository: SeasonRepositoryImpl
) {
    suspend fun createSeason(): Long = seasonRepository.insertSeason(Season())
}