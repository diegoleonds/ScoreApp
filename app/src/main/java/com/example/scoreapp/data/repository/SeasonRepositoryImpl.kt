package com.example.scoreapp.data.repository

import com.example.scoreapp.data.database.SeasonDAO
import com.example.scoreapp.data.model.Season
import com.example.scoreapp.domain.repository.SeasonRepository

class SeasonRepositoryImpl(
    val dao: SeasonDAO
) : SeasonRepository {
    override suspend fun getAll(): List<Season> {
        return dao.getAll()
    }

    override suspend fun updateSeason(season: Season) {
        dao.update(season)
    }

    override suspend fun deleteSeason(season: Season) {
        dao.delete(season)
    }

    override suspend fun insertSeason(season: Season): Long =
        dao.insert(season)
}