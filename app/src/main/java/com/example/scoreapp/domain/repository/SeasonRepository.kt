package com.example.scoreapp.domain.repository

import com.example.scoreapp.data.model.Season

interface SeasonRepository {

    suspend fun getAll(): List<Season>
    suspend fun updateSeason(season: Season)
    suspend fun deleteSeason(season: Season)
    suspend fun insertSeason(season: Season)
}