package com.example.scoreapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scoreapp.R
import com.example.scoreapp.data.model.Game
import kotlinx.android.synthetic.main.item_game.view.*

class GameAdapter(
    private val click: AdapterClick<Game>
) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {
    lateinit var games: ArrayList<Game>
    var maxGamePosition: Int? = null
    var minGamePoisition: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_game, parent, false)
        return GameViewHolder(itemView)
    }

    override fun getItemCount(): Int = if (this::games.isInitialized) games.size else 0

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games.get(position)
        holder.gameNumberTextView.text =
            holder.itemView.context.getString(R.string.game) + " " + game.id
        holder.gamePointsTextView.text = game.points.toString()
        holder.gameTrophyImageView.visibility = View.GONE

        setTrophyIconToMaxRecordGame(position, holder)
        setTrophyIconToMinRecordGame(position, holder)
        holder.gameCardView.setOnClickListener{ click.simpleClick(game) }
        holder.gameCardView.setOnLongClickListener { click.longClick(game) }
    }

    fun setTrophyIconToMaxRecordGame(position: Int, holder: GameViewHolder){
        maxGamePosition?.let {
            if (position == it){
                holder.gameTrophyImageView.setImageResource(R.drawable.ic_gold_trophy)
                holder.gameTrophyImageView.visibility = View.VISIBLE
            }
        }
    }

    fun setTrophyIconToMinRecordGame(position: Int, holder: GameViewHolder){
        minGamePoisition?.let {
            if (position == it){
                holder.gameTrophyImageView.setImageResource(R.drawable.ic_wood_trophy)
                holder.gameTrophyImageView.visibility = View.VISIBLE
            }
        }
    }

    fun removeTrophyFromItem(position: Int?){
        position?.let { notifyItemChanged(position) }
    }


    class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gameNumberTextView = itemView.gameItemTextView
        val gamePointsTextView = itemView.gamePointsItemTextView
        val gameTrophyImageView = itemView.gameTrophy
        val gameCardView = itemView.gameCardView
    }
}