package com.jmlatham.eventscheduler

import EmailPasswordLoginModel
import FirebaseLoginClass
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var myAuth: FirebaseAuth
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var errorMessage: TextView
    private val flc: FirebaseLoginClass = FirebaseLoginClass()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        myAuth = Firebase.auth
        edtEmail = findViewById(R.id.username)
        edtPassword = findViewById(R.id.password)
        errorMessage = findViewById(R.id.errorMessageView)

        if(intent.getStringExtra("message") != null){
            showError(intent.getStringExtra("message")!!)
        }

        if(intent.getStringExtra("email") != null){
            edtEmail.text.replace(0, edtEmail.text.length, intent.getStringExtra("email"))
        }

        btnLogin = findViewById(R.id.login)
        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            val eplm = EmailPasswordLoginModel(email, password)

            if(emailAndPasswordAreValid(email, password)) {
                flc.loginWithUserNameAndPassword(eplm, onSuccess, onFailure)
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        if(myAuth.currentUser != null) {
            toastUser("Authentication Successful!")
            navigateToMainActivity(myAuth.currentUser)
        }
    }

    private fun emailAndPasswordAreValid(email: String, password: String): Boolean {
        if (!emailIsValid(email)){
            showError("Email is not in the correct format.")
            return false
        }
        return emailIsValid(email) && passwordIsValid(password)
    }

    private fun passwordIsValid(password: String): Boolean {
        val minimumNumberOfCharacters = 8
        if(password.length >= minimumNumberOfCharacters) {
            clearError()
            return true
        }
        showError("The password must be longer than $minimumNumberOfCharacters characters")
        return false
    }

    private fun emailIsValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun navigateToMainActivity(user: FirebaseUser?) {
        showError("")
        if (user != null) {
            val intent = Intent(this, MainActivity::class.java).apply {
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

    private fun showError(message: String){
        errorMessage.text = message
    }

    private fun clearError(){
        errorMessage.text = ""
    }
}