package com.example.scoreapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scoreapp.R
import com.example.scoreapp.data.model.Game
import kotlinx.android.synthetic.main.item_game.view.*

class GameAdapter(
    val click: Click<Game>
) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {
    lateinit var games: ArrayList<Game>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_game, parent, false)
        return GameViewHolder(itemView)
    }

    override fun getItemCount(): Int = if (this::games.isInitialized) games.size else 0

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games.get(position)
        holder.gameNumberTextView.text =
            holder.itemView.context.getString(R.string.game_item_text_view) + " " + game.id
        holder.gamePointsTextView.text = game.points.toString()

        holder.gameCardView.setOnClickListener{ click.simpleClick(game) }
        holder.gameCardView.setOnLongClickListener { click.longClick(game) }
    }

    class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gameNumberTextView = itemView.gameItemTextView
        val gamePointsTextView = itemView.gamePointsItemTextView
        val gameTrophyImageView = itemView.gameTrophy
        val gameCardView = itemView.gameCardView
    }
}