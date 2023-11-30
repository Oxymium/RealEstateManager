package com.oxymium.realestatemanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.realestatemanager.database.estate.EstateRepository
import com.oxymium.realestatemanager.model.CategoryHelper
import com.oxymium.realestatemanager.model.databaseitems.Estate
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MapSelectedViewModel(val estateRepository: EstateRepository): ViewModel() {

    val selectedEstate: LiveData<Estate?> get() = _selectedEstate
    private val _selectedEstate = MutableLiveData<Estate?>(null)
    fun updateSelectedEstate(estate: Estate?){
        _selectedEstate.value = estate
    }
    fun getSelectedEstate(id: Long) =
        viewModelScope.launch {
        updateSelectedEstate(
            estateRepository.getEstateWithId(id).first()
        )
    }

    // Icon helper
    val iconHelper: LiveData<CategoryHelper?> get() = _iconHelper
    private val _iconHelper = MutableLiveData<CategoryHelper?>(null)
    fun updateIconHelper(categoryHelper: CategoryHelper?){
        _iconHelper.value = categoryHelper
    }

    fun onClickIcons(value: Int){
        updateIconHelper(
            when(value){
                1 -> CategoryHelper.CategoryEnergyClass
                2 -> CategoryHelper.CategoryRooms
                3 -> CategoryHelper.CategoryBedrooms
                4 -> CategoryHelper.CategoryBathrooms
                5 -> CategoryHelper.CategoryInternet
                6 -> CategoryHelper.CategoryFurnished
                7 -> CategoryHelper.CategoryGarden
                8 -> CategoryHelper.CategoryDisabledAccessibility
                else -> CategoryHelper.CategoryEnergyClass
            })
    }

    // Details button
    val detailsButtonWasClicked: LiveData<Boolean?> get() = _detailsButtonWasClicked
    private val _detailsButtonWasClicked = MutableLiveData<Boolean?>(null)
    fun toggleUpdateButtonWasClicked(boolean: Boolean?){
        _detailsButtonWasClicked.value = boolean
    }
    fun onClickDetailsButton(){
        toggleUpdateButtonWasClicked(true)
    }

}