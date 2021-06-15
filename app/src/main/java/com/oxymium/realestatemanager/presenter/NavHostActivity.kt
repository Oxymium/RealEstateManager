package com.oxymium.realestatemanager.presenter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

import com.oxymium.realestatemanager.R

// -----------------------------------
// NavHostActivity (Launcher Activity)
// -----------------------------------

class NavHostActivity : AppCompatActivity() {

    private val activityTAG = javaClass.simpleName

    private val navController get() = Navigation.findNavController(this, R.id.navHostFragment)

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_host)

        // Trace for debug purposes
        Log.d(activityTAG, "onCreate:")

        initNavigationUI()

        hideSupportActionBar()
        disableWindowAutoResizingWhenKeyboardCalled()

    }


    private fun initNavigationUI(){
        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNav)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }

    private fun hideSupportActionBar() {
        supportActionBar?.hide()
    }

    // Prevents window resizing when virtual keyboard opens
    private fun disableWindowAutoResizingWhenKeyboardCalled() {
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
    }

}