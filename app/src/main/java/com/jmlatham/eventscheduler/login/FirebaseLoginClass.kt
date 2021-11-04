package com.jmlatham.eventscheduler.login

import EmailPasswordLoginModel
import android.content.ContentValues
import android.util.Log
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseLoginClass {
    private var myAuth: FirebaseAuth = Firebase.auth

    fun loginWithUserNameAndPassword(eplm: EmailPasswordLoginModel, onSuccess:(EmailPasswordLoginModel)->Unit, onFailure:(EmailPasswordLoginModel)->Unit) {
        myAuth.signInWithEmailAndPassword(eplm.email, eplm.password).addOnCompleteListener {
                task->
            if(task.isSuccessful){
                logError("Sign in with email and password: successful")
                eplm.user = myAuth.currentUser!!
                eplm.toastMessage = "Welcome!!"
                onSuccess(eplm)
            } else {
                eplm.toastMessage = "User not found in system."
                eplm.errorMessage = "That email and password combination is incorrect. Would you like to register a new user?"
                onFailure(eplm)
            }
        }
    }

    fun createLoginWithUserAndPassword(eplm: EmailPasswordLoginModel, onSuccess:(EmailPasswordLoginModel)->Unit, onFailure:(EmailPasswordLoginModel)->Unit) {
        myAuth.createUserWithEmailAndPassword(eplm.email, eplm.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    eplm.toastMessage = "User created successfully!"
                    eplm.user = myAuth.currentUser!!
                    logError("createUserWithEmail:Success")
                    onSuccess(eplm)
                } else {
                    eplm.toastMessage = "Login Failed: try again"
                    eplm.errorMessage = "Either your password is wrong or you are trying to create a new user with an email that already exists in the system."
                    logError("createUserWithEmail:failure")
                    onFailure(eplm)
                }
            }
    }

    private fun logError(message: String){
        Log.w(ContentValues.TAG, message)
    }

    fun emailAndPasswordAreValid(email: String, password: String, showError:(String)->Unit, clearError:()->Unit): Boolean {
        if (!emailIsValid(email)){
            showError("Email is not in the correct format.")
            return false
        }
        return emailIsValid(email) && passwordIsValid(password, showError, clearError)
    }

    private fun passwordIsValid(password: String, showError: (String) -> Unit, clearError: () -> Unit): Boolean {
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
}