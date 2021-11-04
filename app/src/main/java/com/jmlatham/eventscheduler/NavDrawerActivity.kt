package com.jmlatham.eventscheduler

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.jmlatham.eventscheduler.databinding.ActivityNavDrawerBinding
import com.jmlatham.eventscheduler.models.User
import com.squareup.picasso.Picasso

class NavDrawerActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityNavDrawerBinding
    private var userObj: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadUserObject()

        binding = ActivityNavDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarNavDrawer.toolbar)

        binding.appBarNavDrawer.fab.setOnClickListener { view ->
            Snackbar.make(view, "Today is a Great DAY!!", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show()
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout

        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_nav_drawer)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_event, R.id.nav_notification, R.id.nav_group
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    }

    private fun loadUserObject() {
        val fbUser = Firebase.auth.currentUser!!
        val db = Firebase.firestore
        val docRef = db.collection("users").document(fbUser.uid)
        docRef.get().addOnSuccessListener { documentSnapshot ->
            userObj = documentSnapshot.toObject<User>()
            val textView = findViewById<TextView>(R.id.textView)
            if (textView != null) {
                textView.text = "loadUserObject:" + userObj!!.contactInfo.emailAddress
            }
//            findViewById<ImageView>(R.id.imageView)?.setImageURI(fbUser.photoUrl)
            val imgUserProfile:ImageView = findViewById(R.id.imageView)
            Picasso.get().load(fbUser.photoUrl).into(imgUserProfile)
//            txtWelcome.text = userProfile?.username
//            userProfile?.let { fillProfileForm(it) }
//            getAvatar()
        }
            .addOnFailureListener{
//                txtWelcome.text = it.toString()
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.nav_drawer, menu)
        val textView = findViewById<TextView>(R.id.textView)
        if(userObj != null) {
            textView.text = "onCreateOptionsMenu:" + userObj!!.contactInfo.emailAddress
        }
//        else if(textView != null) {
//            textView.text = "onCreate: userObj is null"
//        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_nav_drawer)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun signOut(email: String){
        Firebase.auth.signOut()
        openLoginActivity(email)
        finish()
    }

    private fun openLoginActivity(email: String) {
        val intent = Intent(this, LoginActivity::class.java).apply {
            putExtra("email", email)
        }
        startActivity(intent)
    }
}