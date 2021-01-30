package com.example.desafio4_firebase.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.desafio4_firebase.GameDetailActivity
import com.example.desafio4_firebase.MainActivity
import com.example.desafio4_firebase.R
import com.example.desafio4_firebase.domain.Game
import kotlinx.android.synthetic.main.activity_game_detail.view.*
import kotlinx.android.synthetic.main.item_game.view.*

class GameAdapter () : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {
    private val games = arrayListOf<Game>()

    class GameViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivImage : ImageView = view.ivCapa
        val tvGameName : TextView = view.tvGameName
        val tvGameAno : TextView = view.tvYear
        val cardView : CardView = view.cvGame
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return GameViewHolder(view)
    }

    override fun getItemCount() = games.size

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games[position]

        holder.tvGameName.text = game.name
        holder.tvGameAno.text = game.year

        Glide.with(holder.ivImage.context).asBitmap()
            .load(game.urlImg)
            .into(holder.ivImage)

        holder.cardView.setOnClickListener {
            val intent = Intent(it.context, GameDetailActivity::class.java)
            intent.putExtra("game", game)
            it.context.startActivity(intent)
        }
    }

    fun addGames(_comics: ArrayList<Game>) {
        var size = this.games.size
        games.addAll(_comics)
        var newSize = this.games.size
        notifyItemRangeChanged(size, newSize)
    }
}