package com.jmlatham.eventscheduler

import EmailPasswordLoginModel
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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jmlatham.eventscheduler.login.FirebaseLoginClass
import com.jmlatham.eventscheduler.models.ContactObj
import com.jmlatham.eventscheduler.models.NameObj
import com.jmlatham.eventscheduler.models.User

class RegisterActivity : AppCompatActivity() {
    private lateinit var myAuth: FirebaseAuth
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtFirstName: EditText
    private lateinit var edtMiddleName: EditText
    private lateinit var edtLastName: EditText
    private lateinit var edtSuffix: EditText
    private lateinit var edtTitle: EditText
    private lateinit var edtNickName: EditText
    private lateinit var edtPhoneNumber: EditText
    private lateinit var edtWebSite: EditText
    private lateinit var btnRegister: Button
    private lateinit var btnCancel: Button
    private lateinit var errorMessage: TextView
    private val flc: FirebaseLoginClass = FirebaseLoginClass()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        myAuth = Firebase.auth
        edtEmail = findViewById(R.id.regEmail)
        edtPassword = findViewById(R.id.regPassword)
        edtFirstName = findViewById(R.id.regFirstName)
        edtMiddleName = findViewById(R.id.regMiddleName)
        edtLastName = findViewById(R.id.regLastName)
        edtSuffix = findViewById(R.id.regSuffix)
        edtTitle = findViewById(R.id.regTitle)
        edtNickName = findViewById(R.id.regNickName)
        edtPhoneNumber = findViewById(R.id.regPhoneNumber)
        edtWebSite = findViewById(R.id.regWebSite)
        errorMessage = findViewById(R.id.regErrorMessageView)

        if(intent.getStringExtra("message") != null){
            showError(intent.getStringExtra("message")!!)
        }

        if(intent.getStringExtra("email") != null){
            edtEmail.text.replace(0, edtEmail.text.length, intent.getStringExtra("email"))
        }

        btnRegister = findViewById(R.id.register)
        btnRegister.setOnClickListener {
            if(formIsValid()) {
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()
                val eplm = EmailPasswordLoginModel(email, password)

                if (flc.emailAndPasswordAreValid(email, password, showError, clearError)) {
                    flc.createLoginWithUserAndPassword(eplm, onSuccess, onFailure)
                }
            } else {
                showError("You must enter an email, password, and first name. All others are optional.")
            }
        }
        btnCancel = findViewById(R.id.regCancel)
        btnCancel.setOnClickListener{
            finish()
        }
    }

    private fun formIsValid(): Boolean {
        return edtEmail.text.isNotBlank() &&
                edtPassword.text.isNotBlank() &&
                edtFirstName.text.isNotBlank()
    }

    private fun navigateToMainActivity(user: FirebaseUser?) {
        showError("")
        if (user != null) {
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
        loadModelsIntoFirebase()
    }

    private fun loadModelsIntoFirebase() {
        val fbUser = Firebase.auth.currentUser
        val nameObj = NameObj(edtFirstName.text.toString(),
            edtMiddleName.text.toString(),
            edtLastName.text.toString(),
            edtSuffix.text.toString(),
            edtTitle.text.toString(),
            edtNickName.text.toString())
        val uid:String = fbUser!!.uid
        val contactObj = ContactObj(edtPhoneNumber.text.toString(),edtEmail.text.toString(), edtWebSite.text.toString())
        val userObj = User(uid, nameObj,contactObj)
        addProfile(userObj)
    }

    private fun addProfile(user: User){
        val db = Firebase.firestore
        db.collection("users").document(user.uid).set(user)
            .addOnSuccessListener {
                navigateToMainActivity(Firebase.auth.currentUser)
            }
            .addOnFailureListener{
                showError(it.toString())
            }
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