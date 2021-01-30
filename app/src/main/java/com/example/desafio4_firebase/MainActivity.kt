package com.example.desafio4_firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.desafio4_firebase.adapter.GameAdapter
import com.example.desafio4_firebase.viewmodel.GameViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val gameAdapter = GameAdapter()
    private val layoutManager = GridLayoutManager(this, 2)

    private val gameViewModel : GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvGame.adapter = gameAdapter
        rvGame.layoutManager = layoutManager

        gameViewModel.games.observe(this, Observer {
            gameAdapter.addGames(it)
        })

        gameViewModel.readGame()

        floatingActionButton.setOnClickListener {
            startActivity(Intent(this, NewGameActivity::class.java))
        }

    }
}