package com.example.securichat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val backButton = findViewById<Button>(R.id.back_button)

        backButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val createAccountButton = findViewById<Button>(R.id.create_account_button)

        val usernameEdit = findViewById(R.id.editTextUsername) as EditText
        val emailEdit = findViewById(R.id.editTextEmailAddress) as EditText
        val passwordEdit = findViewById(R.id.editTextPassword) as EditText
        val confirmPasswordEdit = findViewById(R.id.editTextConfirmPassword) as EditText
        val dbHandler = DBHandler(this)

        createAccountButton.setOnClickListener{

            val intent = Intent(this, MainActivity::class.java)
            val userName: String = usernameEdit.getText().toString()
            val emailAddress: String = emailEdit.getText().toString()
            val password: String = passwordEdit.getText().toString()
            val confirmPassword: String = confirmPasswordEdit.getText().toString()
            var passwordsSame: Boolean = true

            while (passwordsSame) {
                if (userName.isEmpty() || emailAddress.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(this@MainActivity3, "Please enter all the data..", Toast.LENGTH_SHORT).show()
                    break
                } else if (password != confirmPassword) {
                    Toast.makeText(this@MainActivity3, "Password and ConfirmPassword are not the same..", Toast.LENGTH_SHORT).show()
                    break
                }
                else {
                    passwordsSame = false
                    dbHandler.addNewUser(userName, emailAddress, password, confirmPassword)
                    Toast.makeText(applicationContext, "Your account has been created.\nPlease Login.", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                }
            }
        }
    }
}