package com.example.scoreapp.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.scoreapp.R
import com.example.scoreapp.data.model.Game
import com.example.scoreapp.ui.dialog.DialogEvents
import com.example.scoreapp.ui.dialog.GameDialog
import com.example.scoreapp.ui.model.Season
import com.example.scoreapp.ui.viewmodel.GameViewModel
import kotlinx.android.synthetic.main.activity_game_info.*
import kotlinx.android.synthetic.main.cardview_points.*
import kotlinx.android.synthetic.main.cardview_season_info.*
import kotlinx.android.synthetic.main.toolbar_back_icon.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class GameActivity : AppCompatActivity(), DialogEvents {
    val viewModel: GameViewModel by viewModel()
    lateinit var dialog: GameDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_info)

        getIntentExtras()
        observeViewModelGame()
        observeViewModelSeason()
        observeViewModelToast()
        setFieldsData()
        initDialog()
        backToolbarButton.setOnClickListener { finish() }
        setFabClick()
    }

    fun getIntentExtras() {
        viewModel.game.value = intent.getParcelableExtra<Game>(getString(R.string.parcelable_game))
        viewModel.season.value =
            intent.getParcelableExtra<Season>(getString(R.string.parcelable_season))
    }

    fun observeViewModelGame() {
        viewModel.game.observe(this, Observer {
            updateGameFieldsData(it)
            dialog.actualText = it.points.toString()
        })
    }

    fun observeViewModelSeason() {
        viewModel.season.observe(this, Observer {
            updateSeasonFieldsData(it)
        })
    }

    fun observeViewModelToast() {
        viewModel.showToast.observe(this, Observer {
            it?.let {
                if (it) {
                    Toast.makeText(
                        this, getString(R.string.game_score_edited_toast_message),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    fun updateGameFieldsData(game: Game) {
        pointsTextView.text = game.points.toString() + " ${getString(R.string.points)}"
        backToolbarTitle.text = "${getString(R.string.game)} " + game.id
    }

    fun updateSeasonFieldsData(season: Season) {
        seasonRecordPointsTextView.text = season.minScore.toString() +
                " - " + season.maxScore.toString()
    }

    fun setFieldsData() {

        recordInfoTitleTextView.text = getString(R.string.season)
    }

    fun initDialog() {
        dialog = GameDialog(
            title = getString(R.string.edit_game_title_dialog),
            events = this,
            actualText = viewModel.game.value?.points.toString() ?: "0"
        )
    }

    fun setFabClick() {
        fabEditGame.setOnClickListener {
            dialog.show(supportFragmentManager, "")
        }
    }

    override fun positiveClick(insertedText: String) {
        if (insertedText.isNullOrEmpty()) {
            dialog.setError(getString(R.string.empty_textfield_error))
        } else if (insertedText.toInt() == viewModel.game.value?.points) {
            dialog.setError(getString(R.string.score_equals_error))
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.updateGame(
                    updatedPoints = insertedText.toInt()
                )
            }
            dialog.dismiss()
        }
    }

    override fun negativeClick() {
        dialog.dismiss()
    }
}