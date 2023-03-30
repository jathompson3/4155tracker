package com.example.securichat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class SettingsPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_page)

        // save changes
        val confirmChanges = findViewById<Button>(R.id.confirm_changes_button)

        confirmChanges.setOnClickListener{
            val intent = Intent(this, MessagesPage::class.java)
            Toast.makeText(applicationContext, "Account Details Saved", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }


        // back button
        val backButton = findViewById<Button>(R.id.back_button)

        backButton.setOnClickListener{
            finish()
        }
    }
}