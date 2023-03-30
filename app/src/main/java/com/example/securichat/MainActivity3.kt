package com.example.securichat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val backButton = findViewById<Button>(R.id.back_button)

        backButton.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val createAccountButton = findViewById<Button>(R.id.create_account_button)

        createAccountButton.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            Toast.makeText(applicationContext, "Your account has been created.\nPlease Login.", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }
    }
}