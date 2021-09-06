package com.oxymium.realestatemanager.viewmodel

import androidx.lifecycle.*

// -----------------
// CurrencyViewModel
// -----------------

class CurrencyViewModel: ViewModel() {

    // Exchange rate (example: default 1$ = 0.84€)
    var exchangeRate: MutableLiveData<Double> = MutableLiveData(0.84)
    // Currency 1
    var firstCurrency: MutableLiveData<Double> = MutableLiveData(0.00)
    // Currency 2

    val resultCurrency = MediatorLiveData<Double>()

    init{
        resultCurrency.addSource(exchangeRate) {
            rate -> resultCurrency.postValue(rate * firstCurrency.value!!)
        }
        resultCurrency.addSource(firstCurrency) {
            firstCurrency -> resultCurrency.postValue(firstCurrency * exchangeRate.value!!)
        }
    }

}