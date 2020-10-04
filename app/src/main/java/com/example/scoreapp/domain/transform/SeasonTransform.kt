package com.example.scoreapp.domain.transform

import com.example.scoreapp.data.model.Game
import com.example.scoreapp.ui.model.Season as ViewSeason
import com.example.scoreapp.data.model.Season as ModelSeason

/**
 * class to convert between view season and model season
 */
class SeasonTransform {

    fun transformModelSeasonIntoViewSeason(modelSeason: ModelSeason, maxGame: Game?, minGame: Game?): ViewSeason {
        return ViewSeason(
            id = modelSeason.id,
            /**
             * if anyone is null the score will be zero
             */
            maxScore = maxGame?.points ?: 0,
            minScore = minGame?.points ?: 0
        )
    }

    fun transformViewSeasonIntoModelSeason(viewSeason: ViewSeason): ModelSeason =
        ModelSeason(viewSeason.id)
}