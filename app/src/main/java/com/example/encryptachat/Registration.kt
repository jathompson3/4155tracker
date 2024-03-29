package com.example.encryptachat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Registration : AppCompatActivity() {

    private lateinit var  editName: EditText
    private lateinit var  editEmail: EditText
    private lateinit var  editPassword: EditText
    private lateinit var  signupButton: Button
    private lateinit var  mAuth: FirebaseAuth
    private lateinit var  mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        editName = findViewById(R.id.editName)
        editEmail = findViewById(R.id.editEmail)
        editPassword = findViewById(R.id.editPassword)
        signupButton = findViewById(R.id.signupButton)

        signupButton.setOnClickListener {
            val name = editName.text.toString()
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()

            signUp(name, email, password)
        }

    }

    private fun signUp(name: String, email: String, password: String) {
        if (name == "") {
            Toast.makeText(this@Registration, "Name cannot be empty", Toast.LENGTH_SHORT).show()
        } else if (email == "") {
            Toast.makeText(this@Registration, "Email cannot be empty", Toast.LENGTH_SHORT).show()
        } else if (password == "") {
            Toast.makeText(this@Registration, "Password cannot be empty", Toast.LENGTH_SHORT).show()
        } else {
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        addUserToDatabase(name, email, mAuth.currentUser?.uid!!)
                        val intent = Intent(this@Registration, MainActivity::class.java)
                        finish()
                        startActivity(intent)


                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this@Registration, "Please enter the correct credentials", Toast.LENGTH_SHORT).show()

                    }
                }
        }
    }

private fun addUserToDatabase(name: String, email: String, uid: String) {
    mDbRef = FirebaseDatabase.getInstance().getReference()

    mDbRef.child("user").child(uid).setValue(User(name, email, uid))
}


}