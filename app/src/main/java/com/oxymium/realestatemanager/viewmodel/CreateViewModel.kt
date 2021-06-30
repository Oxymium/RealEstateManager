package com.oxymium.realestatemanager.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oxymium.realestatemanager.model.Estate

// ---------------
// CreateViewModel
// ---------------

class CreateViewModel: ViewModel() {

    // Estate to complete during creation form
    var onGoingCreatedEstate: MutableLiveData<Estate> = MutableLiveData()

    var estateType: MutableLiveData<String> = MutableLiveData("Select type")
    var energyScore: MutableLiveData<String> = MutableLiveData("Energy score")

    var wasEstateTypeClicked: MutableLiveData<Boolean> = MutableLiveData(false)
    fun onClickEstateType(){
        wasEstateTypeClicked.postValue(true)
    }

    var wasEstateEnergyScoreClicked: MutableLiveData<Boolean> = MutableLiveData(false)
    fun onClickEnergyScore(){
        wasEstateEnergyScoreClicked.postValue(true)
    }


    //TEST
    val test1: MutableLiveData<String> = MutableLiveData("This is databinded date")
    val test2: MutableLiveData<String> = MutableLiveData("Those are databinded values")



}