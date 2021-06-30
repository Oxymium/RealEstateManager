package com.oxymium.realestatemanager.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// -----------------
// CurrencyViewModel
// -----------------

class CurrencyViewModel: ViewModel() {

    // Exchange rate (example: default 1$ = 0.84€)
    var exchangeRate: MutableLiveData<String> = MutableLiveData("0f")
    // Currency 1
    var firstCurrency: MutableLiveData<String> = MutableLiveData("0f")
    // Currency 2
    var secondCurrency: MutableLiveData<String> = MutableLiveData("0f")

}