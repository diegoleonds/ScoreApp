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
    /**
     * use koin to get it
     */
    val viewModel: SeasonInfoViewModel by viewModel()

    lateinit var season: Season

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_season_info)

        /**
         * get value from intent
         */
        season = intent.getParcelableExtra<Season>(getString(R.string.parcelable_season))

        /**
         * click in toolbar back arrow icon will finish this activity
         */
        backToolbarButton.setOnClickListener { finish() }
        populateViews()
        observeViewModelData()
    }

    override fun onResume() {
        super.onResume()
        /**
         * update viewmodel data when the user comes to this activity
         */
        updateViewModelData()
    }

    /**
     * observe viewmodel game list
     */
    fun observeViewModelData() {
        viewModel.games.observe(this, Observer {
            /**
             * update the layout with the average score
             */
            pointsTextView.text = "${viewModel.getSeasonAverageScore().toString()} " +
                    getString(R.string.points)

            /**
             * update the layout with minimum and maximum frequency of record breaking
             */
            includeSeasonFrequency.seasonRecordPointsTextView.text =
                "${viewModel.getMinRecordFrequency()} - ${viewModel.getMaxRecordFrequency()}"
        })
    }

    fun updateViewModelData(){
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getSeasonGames(season.id)
        }
    }

    /**
     * populate view with data from season and strings resources
     */
    fun populateViews(){
        backToolbarTitle.text = getString(R.string.season_info_activity_title)
        includeSeasonPoints.recordInfoTitleTextView.text =
            getString(R.string.record_points_cardview_title)

        includeSeasonPoints.seasonRecordPointsTextView.text =
            "${season.minScore} - ${season.maxScore}"

        includeSeasonFrequency.recordInfoTitleTextView.text =
            getString(R.string.record_frequency_cardview_title)

        includeSeasonFrequency.seasonMinScoreText.text = getString(R.string.minimum_record)
        includeSeasonFrequency.seasonMaxScoreText.text = getString(R.string.maximum_record)

        pointsTitleTextView.text = getString(R.string.average_score_cardview_title)
    }
}