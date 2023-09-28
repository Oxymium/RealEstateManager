package com.oxymium.realestatemanager.model

import kotlin.math.pow

// -----
// Loan
// -----

data class Loan(
    var borrowedAmount: Float = 0.0f,
    var deposit: Float = 0.0f,
    var duration: Int = 0,
    var interestRate: Float = 0.0f
    ){

    fun monthlyPayment(): Float {
        val totalMonths = duration * 12
        val monthlyInterestRate = interestRate / (12 * 100)
        return (borrowedAmount * (monthlyInterestRate * (1 + monthlyInterestRate).pow(totalMonths))) / ((1 + monthlyInterestRate).pow(totalMonths) - 1)
    }

}