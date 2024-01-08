package com.oxymium.realestatemanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.realestatemanager.SECONDARY_PICTURES_AMOUNT_LIMIT
import com.oxymium.realestatemanager.database.estate.EstateRepository
import com.oxymium.realestatemanager.database.picture.PictureRepository
import com.oxymium.realestatemanager.demoSet1WithAddressAndLatLng
import com.oxymium.realestatemanager.demoSet2WithAddressAndLatLng
import com.oxymium.realestatemanager.demoSet3WithAddressAndLatLng
import com.oxymium.realestatemanager.model.databaseitems.Estate
import com.oxymium.realestatemanager.model.mock.generateOneRandomEstate
import com.oxymium.realestatemanager.model.mock.generateOneRandomPicture
import kotlinx.coroutines.launch

// ------------
// DevViewModel
// ------------

class DevViewModel(private val estateRepository: EstateRepository, private val pictureRepository: PictureRepository): ViewModel() {

    // -------
    // Testing
    // -------

    val givenNumber: LiveData<Int?> get() = _givenNumber
    private val _givenNumber = MutableLiveData<Int?>()

    fun updateGivenNumber(number: Int?){
        _givenNumber.value = number
    }

    // Debug Methods (Delete & Add for testing purposes)
    // Generate any given number of mocked Estate
    fun onClickAddButton(number: Int) {
        for (i in 1..number) {
            insertRandomEstate(generateOneRandomEstate())
        }
    }

    // Insert Estate in DB, returns Estate ID
    private fun insertRandomEstate(estate: Estate) =
        viewModelScope.launch {
            val insertedId: Long = estateRepository.insertEstate(estate)
            val randomAmountOfPictures = (1..SECONDARY_PICTURES_AMOUNT_LIMIT).random()
            for (i in 1 .. randomAmountOfPictures){
                val picture = generateOneRandomPicture()
                picture.estate_id = insertedId
                pictureRepository.insertPicture(picture)
            }
        }

    // Delete all
    fun onClickDeleteAllButton(){ viewModelScope.launch { estateRepository.deleteAllEstates() }}

    // Demo set
    fun onClickDemoSetButton() {
        viewModelScope.launch {
            // INSERT DEMO SETS IN DB
            demoSet1WithAddressAndLatLng.forEach{ estateRepository.insertEstate(it) }
            demoSet2WithAddressAndLatLng.forEach{ estateRepository.insertEstate(it) }
            demoSet3WithAddressAndLatLng.forEach { estateRepository.insertEstate(it) }
        }
    }
}