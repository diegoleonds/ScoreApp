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
    /**
     * use koin to get it
     */
    val viewModel: GameViewModel by viewModel()

    lateinit var editGameDialog: GameDialog

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

    /**
     * get data from intent and set it to viewmodel observers
     */
    fun getIntentExtras() {
        viewModel.game.value = intent.getParcelableExtra<Game>(getString(R.string.parcelable_game))
        viewModel.season.value =
            intent.getParcelableExtra<Season>(getString(R.string.parcelable_season))
    }

    /**
     * observe viewmodel game
     */
    fun observeViewModelGame() {
        viewModel.game.observe(this, Observer {
            updateGameFieldsData(it)
            /**
             * set ediGameDialog text
             */
            editGameDialog.actualText = it.points.toString()
        })
    }

    /**
     * observe viewmodel season
     */
    fun observeViewModelSeason() {
        viewModel.season.observe(this, Observer {
            updateSeasonFieldsData(it)
        })
    }

    /**
     * observe viewmodel toast boolean
     */
    fun observeViewModelToast() {
        viewModel.showToast.observe(this, Observer {
            /**
             * show the toast only if the value is true
             */
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

    /**
     * set cardview title in layout
     */
    fun setFieldsData() {
        recordInfoTitleTextView.text = getString(R.string.season)
    }

    fun initDialog() {
        editGameDialog = GameDialog(
            title = getString(R.string.edit_game_title_dialog),
            events = this,
            actualText = viewModel.game.value?.points.toString() ?: "0"
        )
    }

    /**
     * set floating action button click
     */
    fun setFabClick() {
        fabEditGame.setOnClickListener {
            editGameDialog.show(supportFragmentManager, "")
        }
    }

    /**
     * interface for positive click in editGameDialog
     */
    override fun positiveClick(insertedText: String) {
        if (insertedText.isNullOrEmpty()) {
            editGameDialog.setError(getString(R.string.empty_textfield_error))
        } else if (insertedText.toInt() == viewModel.game.value?.points) {
            editGameDialog.setError(getString(R.string.score_equals_error))
        } else {
            /**
             * update the game and close the dialog
             */
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.updateGame(
                    updatedPoints = insertedText.toInt()
                )
            }
            editGameDialog.dismiss()
        }
    }

    /**
     * interface for negative click in editGameDialog
     */
    override fun negativeClick() {
        /**
         * close the dialog
         */
        editGameDialog.dismiss()
    }
}