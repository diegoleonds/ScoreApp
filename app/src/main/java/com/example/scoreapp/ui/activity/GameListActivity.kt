package com.example.scoreapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scoreapp.R
import com.example.scoreapp.data.model.Game
import com.example.scoreapp.ui.adapter.AdapterClick
import com.example.scoreapp.ui.adapter.GameAdapter
import com.example.scoreapp.ui.dialog.DialogEvents
import com.example.scoreapp.ui.dialog.GameDialog
import com.example.scoreapp.ui.model.Season
import com.example.scoreapp.ui.viewmodel.GameListViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_game_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class GameListActivity : AppCompatActivity(), DialogEvents, AdapterClick<Game> {
    /**
     * use koin to get it
     */
    private val viewModel: GameListViewModel by viewModel()

    /**
     * views from layout
     */
    private lateinit var backBtn: ImageButton
    private lateinit var addGameFab: FloatingActionButton
    private lateinit var recyclerView: RecyclerView

    lateinit var addGameDialog: GameDialog

    /**
     * adapter for the view list,
     * it tells how to show data
     */
    private val adapter = GameAdapter(this)

    /**
     * control variable to avoid double activity initializations,
     * allow to start another activity when it value is true
     */
    private var clickAnotherActivityEnable = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_list)

        /**
         * getting the season from the intent
         */
        val season = intent.getParcelableExtra<Season>(getString(R.string.parcelable_season))
        viewModel.season.value = season

        /**
         * set the activity title
         */
        gameListToolbarTitle.text = "${getString(R.string.season)} ${season.id}"

        initViews()
        initRecyclerView()
        showToastIfSeasonIsNew()
        setBackBtnClick()
        initDialog()
        setFabClick()
        setSeasonChartButtonClick()
        observeViewModelGameList()
    }

    override fun onResume() {
        super.onResume()
        /**
         * update the list data when the user comes to this activity
         */
        updateViewModelGameList()
        clickAnotherActivityEnable = true
    }

    /**
     * get the views from layout
     */
    private fun initViews() {
        backBtn = gameListToolbarBackButton
        addGameFab = gameAddFab
        recyclerView = gamesRecyclerView
    }

    /**
     * configure the season list
     */
    private fun initRecyclerView() {
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun showToastIfSeasonIsNew() {
        if (intent.getBooleanExtra(getString(R.string.is_season_new_intent), false)) {
            showToast(getString(R.string.season_created_toast_message))
        }
    }

    /**
     * update the view list with latest games
     */
    private fun observeViewModelGameList() {
        viewModel.games.observe(this, Observer {
            it?.let {
                updateRecyclerViewAdapter(it)
                viewModel.setMinAndMaxSeasonScoreGames()
            }
        })
    }

    /**
     * @param games to be show on the screen
     */
    private fun updateRecyclerViewAdapter(games: ArrayList<Game>) {
        adapter.games = games
        /**
         * remove the icons trophys
         */
        adapter.removeTrophyFromItem(adapter.maxGamePosition)
        adapter.removeTrophyFromItem(adapter.minGamePoisition)

        /**
         * send the position in the list of games that deserve a trophy
         */
        adapter.maxGamePosition = viewModel.getPositionOfGameWithMaxRecord()
        adapter.minGamePoisition = viewModel.getPositionOfGameWithMinRecord()

        adapter.notifyDataSetChanged()
    }

    private fun updateViewModelGameList() {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.updateGamesList()
        }
    }

    /**
     * @param message to be show in the toast
     */
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * set click in toolbar chart icon
     */
    private fun setSeasonChartButtonClick() {
        gameListChartBtn.setOnClickListener {
            if (clickAnotherActivityEnable) {
                val intent = Intent(this, SeasonInfoActivity::class.java)
                intent.putExtra(getString(R.string.parcelable_season), viewModel.season.value)
                clickAnotherActivityEnable = false
                startActivity(intent)
            }
        }
    }

    /**
     * click in toolbar back arrow icon
     */
    private fun setBackBtnClick() {
        backBtn.setOnClickListener {
            if (clickAnotherActivityEnable) {
                clickAnotherActivityEnable = false
                /**
                 * finish this activity
                 */
                finish()
            }
        }
    }

    private fun initDialog() {
        addGameDialog = GameDialog(
            getString(R.string.add_game_title_dialog),
            this
        )
    }

    /**
     * set click in float action button
     */
    private fun setFabClick() {
        addGameFab.setOnClickListener {
            /**
             * open add gameDialog
             */
            addGameDialog.show(supportFragmentManager, "")
        }
    }

    /**
     * interface for clicks in gameDialog
     */
    override fun positiveClick(insertedText: String) {
        if (insertedText.isNullOrEmpty()) {
            addGameDialog.setError(getString(R.string.empty_textfield_error))
        } else {
            /**
             * add a game
             */
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.addGame(insertedText.toInt())
            }
            /**
             * show toast and close the dialog
             */
            showToast(getString(R.string.game_created_toast_message))
            addGameDialog.dismiss()
        }
    }

    /**
     * interface for clicks in gameDialog,
     * only close the dialog
     */
    override fun negativeClick() {
        addGameDialog.dismiss()
    }

    /**
     * interface for clicks in a list item
     */
    override fun simpleClick(game: Game) {
        if (clickAnotherActivityEnable) {
            /**
             * start game activity with extras
             */
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra(getString(R.string.parcelable_game), game)
            intent.putExtra(getString(R.string.parcelable_season), viewModel.season.value)
            clickAnotherActivityEnable = false
            startActivity(intent)
        }
    }

    /**
     * interface for pressing in a list item
     */
    override fun longClick(game: Game): Boolean {
        /**
         * open a dialog asking for delete the game
         */
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.delete_game_title_dialog))
            .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
            }
            .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                /**
                 * delete the game and show a toast
                 */
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.deleteGame(game)
                }
                showToast(getString(R.string.game_deleted_toast_message))
            }
            .show()
        return true
    }
}