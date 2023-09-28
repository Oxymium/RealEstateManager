package com.oxymium.realestatemanager.model

data class CurrencyConversion(
    var input: Double = 100.00,
    var exchangeRate: Double = 0.84,
    var result: Double = 0.0
) {
    fun convert(){
        result = input * exchangeRate
    }
}