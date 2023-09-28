package com.oxymium.realestatemanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oxymium.realestatemanager.model.CurrencyConversion

// -----------------
// CurrencyViewModel
// -----------------

class CurrencyViewModel: ViewModel() {

    val currencyConversion: LiveData<CurrencyConversion> get() = _currencyConversion
    private val _currencyConversion = MutableLiveData(
        CurrencyConversion(
            input = 100.00,
            exchangeRate = 0.84,
            result = 84.00
        ))
    private fun updateCurrencyConversion(currencyConversion: CurrencyConversion){
        _currencyConversion.value = currencyConversion
    }

    // Update exchange rate
    fun updateExchangeRate(exchangeRate: Double){
        val currentCurrencyConversion = _currencyConversion.value ?: CurrencyConversion()
        currentCurrencyConversion.exchangeRate = exchangeRate
        currentCurrencyConversion.convert()
        updateCurrencyConversion(currentCurrencyConversion)
    }

    // Update input amount
    fun updateInputAmount(inputAmount: Double){
        val currencyConversion = _currencyConversion.value ?: CurrencyConversion()
        currencyConversion.input = inputAmount
        currencyConversion.convert()
        updateCurrencyConversion(currencyConversion)
    }

}