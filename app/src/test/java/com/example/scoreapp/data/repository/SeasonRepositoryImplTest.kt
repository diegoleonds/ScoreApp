package com.example.scoreapp.data.repository

import com.example.scoreapp.data.database.SeasonDAO
import com.example.scoreapp.data.model.Season
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SeasonRepositoryImplTest {
    val dao = mockk<SeasonDAO>()
    val repository = SeasonRepositoryImpl(dao)

    @Test
    fun shouldReturnAllSeasons() = runBlocking {
        val expectedSeasons = ArrayList<Season>()
        for (i in 0 until 10) {
            expectedSeasons.add(
                Season(
                    id = i.toLong()
                )
            )
        }
        coEvery { dao.getAll() } returns expectedSeasons
        assertEquals(expectedSeasons, repository.getAll())
    }

    @Test
    fun shouldReturnTheIdOfTheInsertedSeason() = runBlocking {
        val seasonWithExpectedId = Season(
            id = 1
        )
        coEvery { dao.insert(seasonWithExpectedId) } returns seasonWithExpectedId.id
        assertEquals(seasonWithExpectedId.id, repository.insertSeason(seasonWithExpectedId))
    }
}