package com.example.scoreapp.domain.transform

import com.example.scoreapp.data.model.Game
import org.junit.Assert.*
import org.junit.Test
import com.example.scoreapp.ui.model.Season as ViewSeason
import com.example.scoreapp.data.model.Season as ModelSeason

class SeasonTransformTest {

    val transform: SeasonTransform = SeasonTransform()

    @Test
    fun shouldTransformModelSeasonIntoViewSeason(){
        val modelSeason = ModelSeason(id = 1)
        val maxGame = Game(
            id = 0,
            fkSeason = 0,
            points = 10
        )
        val minGame = Game(
            id = 1,
            fkSeason = 0,
            points = 5
        )
        val expected = ViewSeason(
            id = 1,
            maxRecord = maxGame.points,
            minRecord = minGame.points
        )

        val viewSeason = transform.transformModelSeasonIntoViewSeason(modelSeason, maxGame, minGame)
        assertEquals(expected, viewSeason)
    }

    @Test
    fun shouldTransformViewSeasonIntoModelSeason(){
        val viewSeason = ViewSeason(
            id = 1,
            maxRecord = 10,
            minRecord = 2
        )
        val expectedModelSeason = ModelSeason(
            id = 1
        )

        assertEquals(expectedModelSeason, transform.transformViewSeasonIntoModelSeason(viewSeason))
    }
}