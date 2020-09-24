package com.example.scoreapp.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scoreapp.data.model.Season

class SeasonAdapter(
    val seasons: List<Season>
) : RecyclerView.Adapter<SeasonAdapter.SeasonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class SeasonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }
}