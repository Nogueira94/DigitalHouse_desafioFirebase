package com.example.desafio4_firebase

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.desafio4_firebase.domain.Game
import com.example.desafio4_firebase.viewmodel.GameViewModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_new_game.*
import java.sql.Timestamp
import java.text.SimpleDateFormat

class NewGameActivity : AppCompatActivity() {

    lateinit var alertDialog : AlertDialog
    lateinit var storageReference: StorageReference
    private val CODE_IMG = 1000

    private var imageUrl = ""

    private val gameViewModel : GameViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)

        llPhoto.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "captura imagem"), CODE_IMG)
        }

        btnSaveGame.setOnClickListener {
            val game = Game(
                etName.text.toString(),
                etYear.text.toString(),
                etDesc.text.toString(),
                imageUrl)
            gameViewModel.saveGame(game)
        }

        gameViewModel.response.observe(this, Observer {
            if (it == "SUCCESS") {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })

        config()

    }

    fun config() {
        alertDialog = SpotsDialog.Builder().setContext(this).build()
        val sdf = SimpleDateFormat("yyyyMMddHHmmss")
        val timestamp = Timestamp(System.currentTimeMillis())
        val timestampString = sdf.format(timestamp)
        storageReference = FirebaseStorage.getInstance().getReference(timestampString)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODE_IMG) {
            alertDialog.show()
            val uploadTask = storageReference.putFile(data!!.data!!)
            uploadTask.continueWithTask { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Imagem carregada", Toast.LENGTH_SHORT).show()
                }
                storageReference!!.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    val url = downloadUri!!.toString()
                        .substring(0, downloadUri.toString().indexOf("&token"))
                    imageUrl = url
                    Log.i("Link direto", url)
                    alertDialog.dismiss()
                }
            }
        }
    }

}