package com.oxymium.realestatemanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.oxymium.realestatemanager.database.AgentRepository
import com.oxymium.realestatemanager.database.EstateRepository
import com.oxymium.realestatemanager.database.PictureRepository

// ----------------------
// EstateViewModelFactory
// ----------------------

class EstateViewModelFactory(private val agentRepository: AgentRepository, private val estateRepository: EstateRepository, private val pictureRepository: PictureRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EstateViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EstateViewModel(agentRepository, estateRepository, pictureRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}