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
import kotlinx.android.synthetic.main.activity_season_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


class SeasonListActivity : AppCompatActivity(), AdapterClick<Season> {
    /**
     * use koin to get it
     */
    private val viewModel: SeasonListViewModel by viewModel()

    /**
     * views from layout
     */
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton

    /**
     * adapter for the view list,
     * it tells how to show data
     */
    private val adapter: SeasonAdapter = SeasonAdapter(this)

    /**
     * control variable to avoid double activity initializations,
     * allow to start another activity when it value is true
     */
    private var isClickToGameListEnable = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_season_list)

        initViews()
        initRecyclerView()
        setFabClick()
        observeSeasonsDataChanges()
        observeViewModelSeasonId()
    }

    override fun onResume() {
        super.onResume()
        /**
         * update the list data when the user comes to this activity
         */
        updateSeasonsData()
    }

    override fun onRestart() {
        super.onRestart()
        isClickToGameListEnable = true
    }

    /**
     * get the views from layout
     */
    private fun initViews() {
        recyclerView = seasonRecyclerView
        fab = seasonAddFab
    }

    /**
     * configure the season list
     */
    private fun initRecyclerView() {
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    /**
     *
     */
    private fun observeSeasonsDataChanges() {
        viewModel.seasonsList.observe(this, Observer {
            it?.let {
                adapter.seasons = ArrayList(it)
                adapter.notifyDataSetChanged()
            }
        })
    }

    /**
     * update season list with the latest data
     */
    private fun updateSeasonsData(){
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.updateSeasonsList()
        }
    }

    /**
     * set an action for the floating action button
     */
    private fun setFabClick() {
        fab.setOnClickListener {
            if (isClickToGameListEnable) {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.createSeason()
                }
            }
        }
    }

    private fun observeViewModelSeasonId() {
        viewModel.seasonId.observe(this, Observer {
            /**
             * is called when a season is created
             */
            initGameListActivity(
                Season(
                    id = it,
                    maxScore = 0,
                    minScore = 0
                )
                , true
            )
            isClickToGameListEnable = false
        })
    }

    /**
     * @param season the ui season to pass to next activity
     *
     * @param isSeasonNew (optional with default value as false) will be true
     * when a new season is created
     */
    private fun initGameListActivity(season: Season, isSeasonNew: Boolean = false) {
        if (isClickToGameListEnable) {
            /**
             * this intent will be sent to next activity with the extras
             */
            val intent = Intent(this, GameListActivity::class.java)
            intent.putExtra(getString(R.string.parcelable_season), season)
            intent.putExtra(getString(R.string.is_season_new_intent), isSeasonNew)
            startActivity(intent)
        }
    }

    private fun deleteSeason(season: Season) {
        /**
         * remove the season only from the adapter array list
         * and notify that data has been changed updating the
         * view list
         */
        adapter.seasons.remove(season)
        adapter.notifyDataSetChanged()

        /**
         * remove the season from the database
         */
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
        /**
         * open a dialog asking for delete the season
         */
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.delete_season_title_dialog))
            .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
            }
            .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                /**
                 * delete the season and show a notification
                 * for it
                 */
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