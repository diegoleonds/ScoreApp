package com.example.scoreapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scoreapp.R
import com.example.scoreapp.data.model.Game
import com.example.scoreapp.ui.adapter.AdapterClick
import com.example.scoreapp.ui.adapter.GameAdapter
import com.example.scoreapp.ui.dialog.DialogClick
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

class GameListActivity : AppCompatActivity(), DialogClick, AdapterClick<Game> {

    private val viewModel: GameListViewModel by viewModel()
    private lateinit var backBtn: ImageButton
    private lateinit var addGameFab: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private val adapter = GameAdapter(this)
    private var clickAnotherActivityEnable = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_list)

        val season = intent.getParcelableExtra<Season>(getString(R.string.parcelable_season))
        viewModel.season.value = season
        gameListToolbarTitle.text = "Season ${season.id}"

        initViews()
        initRecyclerView()
        setBackBtnClick()
        setFabClick()
        setSeasonChartButtonClick()
        observeViewModelGameList()
    }

    override fun onResume() {
        super.onResume()
        updateViewModelGameList()
        clickAnotherActivityEnable = true
    }

    private fun initViews() {
        backBtn = gameListToolbarBackButton
        addGameFab = gameAddFab
        recyclerView = gamesRecyclerView
    }

    private fun initRecyclerView() {
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun observeViewModelGameList() {
        viewModel.games.observe(this, Observer {
            it?.let {
                updateRecyclerViewAdapter(it)
                viewModel.updateGamesOfCurrentSeason()
            }
        })
    }

    private fun updateRecyclerViewAdapter(games: ArrayList<Game>) {
        adapter.games = games
        adapter.removeTrophyFromItem(adapter.maxGamePosition)
        adapter.removeTrophyFromItem(adapter.minGamePoisition)
        adapter.maxGamePosition = viewModel.getPositionOfGameWithMaxRecord()
        adapter.minGamePoisition = viewModel.getPositionOfGameWithMinRecord()
        adapter.notifyDataSetChanged()
    }

    private fun updateViewModelGameList() {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.updateGamesList()
        }
    }

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

    private fun setBackBtnClick() {
        backBtn.setOnClickListener {
            if (clickAnotherActivityEnable) {
                clickAnotherActivityEnable = false
                finish()
            }
        }
    }

    private fun setFabClick() {
        addGameFab.setOnClickListener {
            val dialog = GameDialog(getString(R.string.add_game_title_dialog), this)
            dialog.show(supportFragmentManager, "")
        }
    }

    override fun positiveClick(insertedText: String) {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.addGame(insertedText.toInt())
        }
    }

    override fun negativeClick() {}

    override fun simpleClick(game: Game) {
        if (clickAnotherActivityEnable) {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra(getString(R.string.parcelable_game), game)
            intent.putExtra(getString(R.string.parcelable_season), viewModel.season.value)
            clickAnotherActivityEnable = false
            startActivity(intent)
        }
    }

    override fun longClick(game: Game): Boolean {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.delete_game_title_dialog))
            .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
            }
            .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.deleteGame(game)
                }
            }
            .show()
        return true
    }
}