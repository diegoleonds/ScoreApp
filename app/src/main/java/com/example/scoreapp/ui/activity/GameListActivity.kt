package com.example.scoreapp.ui.activity

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.scoreapp.R
import com.example.scoreapp.ui.model.Season
import com.example.scoreapp.ui.viewmodel.GameListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_game_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class GameListActivity : AppCompatActivity() {

    private val viewModel: GameListViewModel by viewModel()
    private lateinit var backBtn: ImageButton
    private lateinit var addGameFab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_list)

        viewModel.season.value =
            intent.getParcelableExtra(getString(R.string.parcelable_season))
        initViews()
        setBackBtnClick()
        setFabClick()
    }

    private fun initViews(){
        backBtn = gameBackBtn
        addGameFab = gameAddFab
    }

    private fun setBackBtnClick(){
        backBtn.setOnClickListener {
            finish()
        }
    }

    private fun setFabClick(){
        addGameFab.setOnClickListener{
            //viewModel.addGame()
        }
    }
}