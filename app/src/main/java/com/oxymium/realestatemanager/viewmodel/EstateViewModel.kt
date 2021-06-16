package com.oxymium.realestatemanager.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oxymium.realestatemanager.model.Estate

// ---------------
// EstateViewModel
// ---------------

class EstateViewModel() : ViewModel() {

    var selectedEstate: MutableLiveData<Estate> = MutableLiveData(null)

    var estates: MutableLiveData<List<Estate>> = MutableLiveData()

}