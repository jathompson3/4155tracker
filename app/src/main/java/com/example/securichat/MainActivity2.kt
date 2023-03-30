package com.example.securichat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val backButton = findViewById<Button>(R.id.back_button)

        backButton.setOnClickListener {
            //val intent = Intent(this, MainActivity::class.java)
            //startActivity(intent)
            finish()
        }

        val loginButton = findViewById<Button>(R.id.login_button)

        loginButton.setOnClickListener {
            val intent = Intent(this, MessagesPage::class.java)
            startActivity(intent)
        }
    }
}