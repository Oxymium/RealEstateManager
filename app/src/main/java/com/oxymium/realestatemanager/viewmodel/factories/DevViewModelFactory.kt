package com.oxymium.realestatemanager.viewmodel.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.oxymium.realestatemanager.database.estate.EstateRepository
import com.oxymium.realestatemanager.database.picture.PictureRepository
import com.oxymium.realestatemanager.viewmodel.DevViewModel

class DevViewModelFactory(private val estateRepository: EstateRepository, private val pictureRepository: PictureRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DevViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DevViewModel(estateRepository, pictureRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}