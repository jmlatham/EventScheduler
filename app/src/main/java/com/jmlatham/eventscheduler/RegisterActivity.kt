package com.jmlatham.eventscheduler

import EmailPasswordLoginModel
import FirebaseLoginClass
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var myAuth: FirebaseAuth
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var errorMessage: TextView
    private lateinit var alertDialog: AlertDialog
    private val flc: FirebaseLoginClass = FirebaseLoginClass()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        myAuth = Firebase.auth
        edtEmail = findViewById(R.id.regUsername)
        edtPassword = findViewById(R.id.regPassword)
        errorMessage = findViewById(R.id.regErrorMessageView)

        if(intent.getStringExtra("message") != null){
            showError(intent.getStringExtra("message")!!)
        }

        if(intent.getStringExtra("email") != null){
            edtEmail.text.replace(0, edtEmail.text.length, intent.getStringExtra("email"))
        }

        btnRegister = findViewById(R.id.register)
        btnRegister.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            val eplm = EmailPasswordLoginModel(email, password)

            if(flc.emailAndPasswordAreValid(email, password, showError, clearError)) {
                flc.createLoginWithUserAndPassword(eplm, onSuccess, onFailure)
            }
        }
    }

    private fun navigateToMainActivity(user: FirebaseUser?) {
        showError("")
        if (user != null) {
//            val intent = Intent(this, MainActivity::class.java).apply {
            val intent = Intent(this, NavDrawerActivity::class.java).apply {
                putExtra("email", user.email)
                putExtra("displayName", user.displayName)
                putExtra("phoneNumber", user.phoneNumber)
                putExtra("photoUrl", user.photoUrl)
                putExtra("user", user.toString())
            }
            startActivity(intent)
            finish()
        }
    }

    private val onSuccess = fun(eplm: EmailPasswordLoginModel){
        toastUser(eplm.toastMessage)
        navigateToMainActivity(eplm.user)
    }

    private val onFailure = fun(eplm: EmailPasswordLoginModel){
        toastUser(eplm.toastMessage)
        showError(eplm.errorMessage)
    }

    private fun toastUser(message:String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private val showError = fun(message: String){
        errorMessage.text = message
    }

    private val clearError = fun(){
        errorMessage.text = ""
    }
}