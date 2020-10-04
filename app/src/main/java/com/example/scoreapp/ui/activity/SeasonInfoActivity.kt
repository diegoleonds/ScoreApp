package com.example.scoreapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.scoreapp.R
import com.example.scoreapp.ui.model.Season
import com.example.scoreapp.ui.viewmodel.SeasonInfoViewModel
import kotlinx.android.synthetic.main.activity_season_info.*
import kotlinx.android.synthetic.main.cardview_points.*
import kotlinx.android.synthetic.main.cardview_season_info.view.*
import kotlinx.android.synthetic.main.toolbar_back_icon.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class SeasonInfoActivity() : AppCompatActivity() {

    val viewModel: SeasonInfoViewModel by viewModel()
    lateinit var season: Season

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_season_info)

        season = intent.getParcelableExtra<Season>(getString(R.string.parcelable_season))
        backToolbarButton.setOnClickListener { finish() }
        populateViews()
        observeViewModelData()
    }

    override fun onResume() {
        super.onResume()
        updateViewModelData()
    }

    fun observeViewModelData() {
        viewModel.games.observe(this, Observer {
            pointsTextView.text = "${viewModel.getSeasonAverageScore().toString()} points"
            includeSeasonFrequency.seasonRecordPointsTextView.text =
                "${viewModel.getMinRecordFrequency()} - ${viewModel.getMaxRecordFrequency()}"
        })
    }

    fun updateViewModelData(){
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getSeasonGames(season.id)
        }
    }

    fun populateViews(){
        backToolbarTitle.text = getString(R.string.season_info_activity_title)
        includeSeasonPoints.recordInfoTitleTextView.text =
            getString(R.string.record_points_cardview_title)

        includeSeasonPoints.seasonRecordPointsTextView.text =
            "${season.minRecord} - ${season.maxRecord}"

        includeSeasonFrequency.recordInfoTitleTextView.text =
            getString(R.string.record_frequency_cardview_title)

        includeSeasonFrequency.seasonMinScoreText.text = getString(R.string.minimum_record)
        includeSeasonFrequency.seasonMaxScoreText.text = getString(R.string.maximum_record)

        pointsTitleTextView.text = getString(R.string.average_score_cardview_title)
    }
}