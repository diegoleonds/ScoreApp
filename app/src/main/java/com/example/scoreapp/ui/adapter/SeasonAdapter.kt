package com.example.scoreapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scoreapp.R
import com.example.scoreapp.ui.model.Season
import kotlinx.android.synthetic.main.item_season.view.*

/**
 * @param click interface for click events
 */
class SeasonAdapter(
    val click: AdapterClick<Season>
) : RecyclerView.Adapter<SeasonAdapter.SeasonViewHolder>() {
    /**
     * list with the data to be show
     */
    lateinit var seasons: ArrayList<Season>

    /**
     * Create new views
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_season, parent, false)
        return SeasonViewHolder(itemView)
    }

    /**
     * Replace the contents of a view
     */
    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) {
        val season = seasons.get(position)
        holder.maxRecordTextView.text = season.maxScore.toString()
        holder.minRecordTextView.text = season.minScore.toString()
        holder.seasonNumberTextView.text =
            holder.itemView.context.getString(R.string.season) + " " + season.id.toString()

        /**
         * set clicks events with the interface
         */
        holder.cardview.setOnClickListener { click.simpleClick(season) }
        holder.cardview.setOnLongClickListener { click.longClick(season) }
    }

    /**
     * Return the size of seasons list
     */
    override fun getItemCount(): Int = if (this::seasons.isInitialized) seasons.size else 0

    /**
     * Provide a reference to the views for each data item.
     */
    class SeasonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val maxRecordTextView = itemView.seasonMaximumScoreTextView
        val minRecordTextView = itemView.seasonMinimumScoreTextView
        val seasonNumberTextView = itemView.seasonNumberTextView
        val cardview = itemView.seasonCardView
    }
}