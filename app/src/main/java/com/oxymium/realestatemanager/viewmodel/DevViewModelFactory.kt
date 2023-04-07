package com.oxymium.realestatemanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.oxymium.realestatemanager.database.EstateRepository
import com.oxymium.realestatemanager.database.PictureRepository

class DevViewModelFactory(private val estateRepository: EstateRepository, private val pictureRepository: PictureRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DevViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DevViewModel(estateRepository, pictureRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}