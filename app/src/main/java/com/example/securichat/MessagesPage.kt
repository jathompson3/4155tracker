package com.example.securichat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast

class MessagesPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages_page)

        // user 1
        val firstContact = findViewById<Button>(R.id.firstContact)

        firstContact.setOnClickListener{
            Toast.makeText(applicationContext, "Opening Message from User 1 ... in the future", Toast.LENGTH_LONG).show()
        }

        // user 2
        val secondContact = findViewById<Button>(R.id.secondContact)

        secondContact.setOnClickListener{
            Toast.makeText(applicationContext, "Opening Message from User 2 ... in the future", Toast.LENGTH_LONG).show()
        }

        // user 3
        val thirdContact = findViewById<Button>(R.id.thirdContact)

        thirdContact.setOnClickListener{
            Toast.makeText(applicationContext, "Opening Message from User 3 ... in the future", Toast.LENGTH_LONG).show()
        }

        // settings
        val settingsButton = findViewById<Button>(R.id.settings_button)

        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsPage::class.java)
            startActivity(intent)
        }

        // logout
        val logoutButton = findViewById<Button>(R.id.logout_button)

        logoutButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}