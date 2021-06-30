package com.oxymium.realestatemanager.presenter

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.database.EstatesApplication
import com.oxymium.realestatemanager.model.Estate
import com.oxymium.realestatemanager.viewmodel.EstateViewModel
import com.oxymium.realestatemanager.viewmodel.EstateViewModelFactory
import com.oxymium.realestatemanager.viewmodel.ToolsViewModel

// -----------------------------------
// NavHostActivity (Launcher Activity)
// -----------------------------------

class NavHostActivity : AppCompatActivity() {

    private val activityTAG = javaClass.simpleName

    private val navController get() = Navigation.findNavController(this, R.id.navHostFragment)
    private lateinit var bottomNavigationView: BottomNavigationView

    // ViewModel injection
    private val estateViewModel: EstateViewModel by viewModels {
        EstateViewModelFactory((application as EstatesApplication).repository)
    }

    private val toolsViewModel: ToolsViewModel by viewModels ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_host)

        // Trace for debug purposes
        Log.d(activityTAG, "onCreate:")

        initNavigationUI()

        observeSelectedEstate()
        observeToolsSelectedCategory()

        hideSupportActionBar()
        disableWindowAutoResizingWhenKeyboardCalled()

    }

    // Navigate from EstatesFragment -> DetailsFragment onClick
    private fun observeSelectedEstate(){
        estateViewModel.selectedEstate.observe(this, { estate ->
            if (estate != null) {
                navController.navigate(R.id.action_estatesFragment_to_detailsFragment)
            }
        })
    }

    private fun observeToolsSelectedCategory(){
        toolsViewModel.categoryClicked.observe(this, { categoryValue ->
            when (categoryValue) {
                1 -> navController.navigate(R.id.action_toolsFragment_to_currencyFragment)
                2 -> navController.navigate(R.id.action_toolsFragment_to_loanFragment)
                3 -> navController.navigate(R.id.action_toolsFragment_to_devFragment)
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