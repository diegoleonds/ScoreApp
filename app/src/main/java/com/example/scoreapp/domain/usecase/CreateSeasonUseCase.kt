package com.example.scoreapp.domain.usecase

import com.example.scoreapp.data.model.Season
import com.example.scoreapp.domain.repository.SeasonRepository

class CreateSeasonUseCase(
    val seasonRepository: SeasonRepository
) {
    suspend fun createSeason(): Long = seasonRepository.insertSeason(Season())
}