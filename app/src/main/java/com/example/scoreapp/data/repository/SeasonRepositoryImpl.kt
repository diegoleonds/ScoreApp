package com.example.scoreapp.data.repository

import com.example.scoreapp.data.database.SeasonDAO
import com.example.scoreapp.data.model.Season
import com.example.scoreapp.domain.repository.SeasonRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class SeasonRepositoryImpl() : SeasonRepository, KoinComponent {
    val dao: SeasonDAO by inject()

    override suspend fun getAll(): List<Season> {
        return dao.getAll()
    }

    override suspend fun updateSeason(season: Season) {
        dao.update(season)
    }

    override suspend fun deleteSeason(season: Season) {
        dao.delete(season)
    }

    override suspend fun insertSeason(season: Season) {
        dao.insert(season)
    }
}