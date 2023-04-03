package com.oxymium.realestatemanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.realestatemanager.RANDOM_PICTURES
import com.oxymium.realestatemanager.SECONDARY_PICTURES_AMOUNT_LIMIT
import com.oxymium.realestatemanager.database.EstateRepository
import com.oxymium.realestatemanager.database.PictureRepository
import com.oxymium.realestatemanager.generateRandomEstate
import com.oxymium.realestatemanager.model.Estate
import kotlinx.coroutines.launch

// ------------
// DevViewModel
// ------------

class DevViewModel(private val estateRepository: EstateRepository, private val pictureRepository: PictureRepository): ViewModel() {

    // -------
    // Testing
    // -------

    val notificationId: LiveData<Long> get() = _notificationId
    private val _notificationId = MutableLiveData<Long>()

    private fun updateNotificationId(id: Long){
        _notificationId.value = id
    }

    val givenNumber: LiveData<Int?> get() = _givenNumber
    private val _givenNumber = MutableLiveData<Int?>()

    fun updateGivenNumber(number: Int?){
        _givenNumber.value = number
    }

    // Debug Methods (Delete & Add for testing purposes)
    // Generate any given number of mocked Estate
    fun onClickAddButton(number: Int) {
        for (i in 1..number) {
            insertRandomEstate(generateRandomEstate())
        }
    }

    // Insert Estate in DB, returns Estate ID
    private fun insertRandomEstate(estate: Estate) =
        viewModelScope.launch {
            val insertedId: Long = estateRepository.insert(estate)
            updateNotificationId(insertedId)
            // TODO provide different number of pictures for testing
            val randomAmountOfPictures = (1..SECONDARY_PICTURES_AMOUNT_LIMIT).random()
            for (i in 1 .. randomAmountOfPictures){
                val picture = RANDOM_PICTURES.random()
                picture.estate_id = insertedId
                pictureRepository.insert(picture)
            }
        }

    // Delete all
    fun onClickDeleteAllButton(){ viewModelScope.launch { estateRepository.deleteAll() }}

}