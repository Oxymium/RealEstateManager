package com.oxymium.realestatemanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.oxymium.realestatemanager.database.EstateRepository

// -------------------
// MapViewModelFactory
// -------------------

class MapSelectedViewModelFactory(private val estateRepository: EstateRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapSelectedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MapSelectedViewModel(estateRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}