package com.example.scoreapp.domain.transform

import com.example.scoreapp.data.model.Game
import com.example.scoreapp.ui.model.Season as ViewSeason
import com.example.scoreapp.data.model.Season as ModelSeason

class SeasonTransform {

    fun transform(modelSeason: ModelSeason, maxGame: Game, minGame: Game): ViewSeason {
        return ViewSeason(
            id = modelSeason.id,
            maxRecord = maxGame.points,
            minRecord = minGame.points
        )
    }
}