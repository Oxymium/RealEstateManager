package com.oxymium.realestatemanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// ----------------------
// EstateViewModelFactory
// ----------------------

class EstateViewModelFactory(): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EstateViewModel::class.java)) {
            return EstateViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class");
    }

}