package com.example.scoreapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scoreapp.R
import com.example.scoreapp.ui.adapter.AdapterClick
import com.example.scoreapp.ui.adapter.SeasonAdapter
import com.example.scoreapp.ui.model.Season
import com.example.scoreapp.ui.viewmodel.SeasonListViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_season_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


class SeasonListActivity : AppCompatActivity(), AdapterClick<Season> {
    private val viewModel: SeasonListViewModel by viewModel()

    //View items
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private val adapter: SeasonAdapter = SeasonAdapter(this)
    private var isClickToGameListEnable = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_season_list)

        initViews()
        initRecyclerView()
        setFabClick()
        setRecyclerViewData()
    }

    override fun onResume() {
        super.onResume()
        setRecyclerViewData()
    }

    override fun onRestart() {
        super.onRestart()
        isClickToGameListEnable = true
    }

    private fun initViews() {
        recyclerView = seasonRecyclerView
        fab = seasonAddFab
    }

    private fun initRecyclerView() {
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setRecyclerViewData() {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.updateSeasonsList()
        }
        viewModel.seasonsList.observe(this, Observer {
            it?.let {
                adapter.seasons = ArrayList(it)
                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun setFabClick() {
        fab.setOnClickListener {
            if (isClickToGameListEnable) {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.createSeason()
                }
                viewModel.seasonId.observe(this, Observer {
                    initGameListActivity(
                        Season(
                            id = it,
                            maxRecord = 0,
                            minRecord = 0
                        )
                        , true
                    )
                    isClickToGameListEnable = false
                })
            }
        }
    }

    private fun initGameListActivity(season: Season, isSeasonNew: Boolean = false) {
        if (isClickToGameListEnable) {
            val intent = Intent(this, GameListActivity::class.java)
            intent.putExtra(getString(R.string.parcelable_season), season)
            intent.putExtra(getString(R.string.is_season_new_intent), isSeasonNew)
            startActivity(intent)
        }
    }

    private fun deleteSeason(season: Season) {
        adapter.seasons.remove(season)
        adapter.notifyDataSetChanged()
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.deleteSeason(season)
        }
    }

    override fun simpleClick(season: Season) {
        if (isClickToGameListEnable) {
            initGameListActivity(season)
            isClickToGameListEnable = false
        }
    }

    override fun longClick(season: Season): Boolean {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.delete_season_title_dialog))
            .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
            }
            .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                deleteSeason(season)
                val toast = Toast.makeText(
                    this,
                    getString(R.string.season_deleted_toast_message), Toast.LENGTH_SHORT
                )
                toast.show()
            }
            .show()
        return true
    }
}