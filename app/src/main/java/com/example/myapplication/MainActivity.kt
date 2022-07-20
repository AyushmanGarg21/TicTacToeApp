package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        val player1 = findViewById<EditText>(R.id.player1)
        val player2 = findViewById<EditText>(R.id.player2)
        val launch:Button = findViewById(R.id.start)
        launch.setOnClickListener {
            if(player1.text.isNotEmpty() && player2.text.isNotEmpty()){
                val intent = Intent(this,MainActivity2::class.java).apply {
                    putExtra("player1",player1.text.toString())
                    putExtra("player2",player2.text.toString())
                }
                finish()
                startActivity(intent)
            }else{
                Toast.makeText(this,"Enter players name",Toast.LENGTH_SHORT).show()
            }
        }
    }
}