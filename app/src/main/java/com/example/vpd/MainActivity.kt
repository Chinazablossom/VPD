package com.example.vpd

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.vpd.views.LoginFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController


        if (isLoggedIn()) {
            if (auth.currentUser == null) {
                navController.navigate(R.id.loginFragment)
            } else {
                supportFragmentManager.beginTransaction()
                    .remove(LoginFragment())
                    .commit()
                navController.navigate(R.id.homeFragment)
            }

        } else {
            navController.navigate(R.id.loginFragment)

        }


    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun isLoggedIn(): Boolean {
        val sharedPreferences = getSharedPreferences("VPDPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

}