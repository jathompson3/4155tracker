package com.example.securichat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val button = findViewById<Button>(R.id.back_button)

        button.setOnClickListener(){
            //val intent = Intent(this, MainActivity::class.java)
            //startActivity(intent)
            finish()
        }
    }
}