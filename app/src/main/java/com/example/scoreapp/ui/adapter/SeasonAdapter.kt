package com.example.scoreapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scoreapp.R
import com.example.scoreapp.ui.model.Season
import kotlinx.android.synthetic.main.item_season.view.*

class SeasonAdapter(
) : RecyclerView.Adapter<SeasonAdapter.SeasonViewHolder>() {
    lateinit var seasons: List<Season>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_season, parent, false)
        return SeasonViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) {
        val season = seasons.get(position)
        holder.maxRecordTextView.text = season.maxRecord.toString()
        holder.minRecordTextView.text = season.minRecord.toString()
    }

    override fun getItemCount(): Int = if (this::seasons.isInitialized) seasons.size else 0

    class SeasonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val maxRecordTextView = itemView.seasonMaximumRecordTextView
        val minRecordTextView = itemView.seasonMinimumRecordTextView
        val seasonNumberTextView = itemView.seasonNumberTextView
    }
}