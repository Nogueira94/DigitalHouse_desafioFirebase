package com.example.desafio4_firebase.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafio4_firebase.domain.Game
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {

    val TAG = "Game View Model"
    var db = FirebaseFirestore.getInstance()
    var cr = db.collection("games")
    val games = MutableLiveData<ArrayList<Game>>()
    val response = MutableLiveData<String>()

    fun readGame() {
        viewModelScope.launch {
            cr.get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = arrayListOf<Game>()
                        for (document in task.result!!) {
                            list.add(
                                Game(
                                    document["name"] as String,
                                    document["year"] as String,
                                    document["desc"] as String,
                                    document["urlImg"] as String
                                )
                            )
                        }
                        games.value = list
                    } else {
                        Log.w(TAG, "Error getting documents.", task.exception)
                    }
                }
        }
    }

    fun saveGame(game: Game){
        viewModelScope.launch {
            val _game: MutableMap<String, Any> = HashMap()
            _game["name"] = game.name
            _game["year"] = game.year
            _game["desc"] = game.desc
            _game["urlImg"] = game.urlImg

            cr.add(_game)
                .addOnSuccessListener { documentReference ->
                    response.value = "SUCCESS"
                }
                .addOnFailureListener { e ->
                    response.value = e.toString()
                }
        }
    }

}