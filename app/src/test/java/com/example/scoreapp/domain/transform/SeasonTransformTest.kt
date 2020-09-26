package com.example.scoreapp.domain.transform

import com.example.scoreapp.data.model.Game
import org.junit.Assert.*
import org.junit.Test
import kotlin.math.min
import com.example.scoreapp.ui.model.Season as ViewSeason
import com.example.scoreapp.data.model.Season as ModelSeason

class SeasonTransformTest {

    val transform: SeasonTransform = SeasonTransform()

    @Test
    fun verifyTransform(){
        val modelSeason = ModelSeason(id = 1)
        val maxGame = Game(
            id = 0,
            fkSeason = 0,
            points = 10,
            maxRecord = true,
            minRecord = false
        )
        val minGame = Game(
            id = 1,
            fkSeason = 0,
            points = 5,
            maxRecord = false,
            minRecord = true
        )

        val expected = ViewSeason(
            id = 1,
            maxRecord = maxGame.points,
            minRecord = minGame.points
        )

        val viewSeason = transform.transform(modelSeason, maxGame, minGame)
        assertEquals(expected, viewSeason)
    }
}