package com.oxymium.realestatemanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.oxymium.realestatemanager.database.AgentRepository
import com.oxymium.realestatemanager.database.EstateRepository
import com.oxymium.realestatemanager.database.PictureRepository
import com.oxymium.realestatemanager.features.create.CreateViewModel

// ----------------------
// CreateViewModelFactory
// ----------------------

class CreateViewModelFactory(private val agentRepository: AgentRepository ,private val estateRepository: EstateRepository, private val pictureRepository: PictureRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CreateViewModel(agentRepository, estateRepository, pictureRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}