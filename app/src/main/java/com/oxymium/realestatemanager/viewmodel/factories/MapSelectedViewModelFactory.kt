package com.oxymium.realestatemanager.viewmodel.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.oxymium.realestatemanager.database.estate.EstateRepository
import com.oxymium.realestatemanager.viewmodel.MapSelectedViewModel

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