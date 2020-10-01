package com.example.scoreapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.scoreapp.R
import com.example.scoreapp.ui.model.Season
import kotlinx.android.synthetic.main.activity_season_info.*
import kotlinx.android.synthetic.main.cardview_points.*
import kotlinx.android.synthetic.main.cardview_record_info.view.*
import kotlinx.android.synthetic.main.toolbar_back_icon.*

class SeasonInfoActivity(): AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_season_info)

        val season = intent.getParcelableExtra<Season>(getString(R.string.parcelable_season))
        backToolbarTitle.text = getString(R.string.season_info_activity_title)
        includeSeasonPoints.recordInfoTitleTextView.text = getString(R.string.record_points_cardview_title)
        includeSeasonPoints.seasonRecordPointsTextView.text =
            "${season.minRecord} - ${season.maxRecord}"

        includeSeasonFrequency.recordInfoTitleTextView.text =
            getString(R.string.record_frequency_cardview_title)

        pointsTitleTextView.text = getString(R.string.average_score_cardview_title)

        backToolbarButton.setOnClickListener { finish() }
    }
}