package com.example.scoreapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scoreapp.R
import com.example.scoreapp.ui.adapter.SeasonAdapter
import com.example.scoreapp.ui.viewmodel.SeasonListViewModel
import kotlinx.android.synthetic.main.activity_season_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class SeasonListActivity : AppCompatActivity() {

    private val viewModel: SeasonListViewModel by viewModel()
    private val adapter: SeasonAdapter = SeasonAdapter()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_season_list)

        initRecyclerView()
        setRecyclerViewData()
    }

    private fun initRecyclerView() {
        recyclerView = seasonRecyclerView
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setRecyclerViewData() {
        viewModel.updateSeasonsList()
        viewModel.seasonsList.observe(this, Observer {
            it?.let {
                adapter.seasons = it
            }
        })
    }


}