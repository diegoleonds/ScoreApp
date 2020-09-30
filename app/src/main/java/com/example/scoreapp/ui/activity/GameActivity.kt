package com.example.scoreapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewStub
import com.example.scoreapp.R
import com.example.scoreapp.data.model.Game
import com.example.scoreapp.ui.model.Season
import kotlinx.android.synthetic.main.activity_game_info.*
import kotlinx.android.synthetic.main.cardview_points.*
import kotlinx.android.synthetic.main.cardview_record_info.*
import kotlinx.android.synthetic.main.toolbar_back_icon.*

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_info)

        val game = intent.getParcelableExtra<Game>(getString(R.string.parcelable_game))
        val season = intent.getParcelableExtra<Season>(getString(R.string.parcelable_season))
        pointsTextView.text = game.points.toString() + " points"
        pointsTitleTextView.text = "Score:"
        recordInfoTitleTextView.text = getString(R.string.record_points_cardview_title)
        backToolbarTitle.text = "Game " + game.id
        seasonRecordPointsTextView.text = season.minRecord.toString() +
                " - " + season.maxRecord.toString()
        backToolbarButton.setOnClickListener { finish() }

    }
}