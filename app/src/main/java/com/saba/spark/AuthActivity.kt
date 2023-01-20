package com.saba.spark

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AuthActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        if (FirebaseAuth.getInstance().currentUser != null) {
            FirebaseDatabase.getInstance().reference.child("profile")
                .child(FirebaseAuth.getInstance().currentUser?.uid.toString())
                .get().addOnSuccessListener {
                    if (it.exists()) {

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()


                    }
                }
        }
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        navController = findNavController(R.id.nav_host_fragment)

    }
}