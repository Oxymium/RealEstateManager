package com.oxymium.realestatemanager.presenter

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.viewmodel.EstateViewModel
import com.oxymium.realestatemanager.viewmodel.EstateViewModelFactory
import com.oxymium.realestatemanager.viewmodel.Injection

// -----------------------------------
// NavHostActivity (Launcher Activity)
// -----------------------------------

class NavHostActivity : AppCompatActivity() {

    private val activityTAG = javaClass.simpleName

    private val navController get() = Navigation.findNavController(this, R.id.navHostFragment)
    private lateinit var bottomNavigationView: BottomNavigationView

    // EstateViewModel
    private lateinit var estateViewModelFactory: EstateViewModelFactory
    private lateinit var estateViewModel: EstateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_host)

        // Trace for debug purposes
        Log.d(activityTAG, "onCreate:")

        initNavigationUI()

        initEstateViewModel()
        observeSelectedEstate()

        hideSupportActionBar()
        disableWindowAutoResizingWhenKeyboardCalled()

    }

    private fun initEstateViewModel(){
        estateViewModel = run {
            estateViewModelFactory = Injection.provideEstateViewModelFactory(this@NavHostActivity)
            ViewModelProvider(this, estateViewModelFactory)[EstateViewModel::class.java]
        }
    }

    // Navigate from EstatesFragment -> DetailsFragment onClick
    private fun observeSelectedEstate(){
        estateViewModel.selectedEstate.observe(this, { estate ->
            if (estate != null) {
                navController.navigate(R.id.action_estatesFragment_to_detailsFragment)
            }
        })
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