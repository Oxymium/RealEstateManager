package com.oxymium.realestatemanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.oxymium.realestatemanager.database.EstateRepository

// ----------------------
// EstateViewModelFactory
// ----------------------

class EstateViewModelFactory(private val estateRepository: EstateRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EstateViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EstateViewModel(estateRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}