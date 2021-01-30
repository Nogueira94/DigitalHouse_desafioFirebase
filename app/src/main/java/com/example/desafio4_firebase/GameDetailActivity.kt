package com.example.desafio4_firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.desafio4_firebase.domain.Game
import kotlinx.android.synthetic.main.activity_game_detail.*
import kotlinx.android.synthetic.main.top_game.view.*

class GameDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_detail)
        setSupportActionBar(topBar.toolbar)

        val game = intent.getSerializableExtra("game") as Game

        tvNameDetail.text = game.name.toString()
        tvYearDetail.text = game.year.toString()
        tvDescription.text = game.desc.toString()

        Log.e("URL",game.urlImg.toString())

        Glide.with(this).asBitmap()
            .load(game.urlImg)
            .into(topBar.ivToolbar)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}