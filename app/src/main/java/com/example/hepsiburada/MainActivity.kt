package com.example.hepsiburada

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavigation()
    }


    private fun setupNavigation() {
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container) as NavHostFragment? ?: return

        val navController: NavController = host.navController
        val myNav: NavigationView = findViewById(R.id.navigationView)
        myNav.setupWithNavController(navController)
    }
}