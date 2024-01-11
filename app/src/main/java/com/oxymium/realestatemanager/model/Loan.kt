package com.oxymium.realestatemanager.model

import kotlin.math.pow

// -----
// Loan
// -----

data class Loan(
    val borrowedAmount: Float = 0.0f,
    val deposit: Float = 0.0f,
    val duration: Int = 0,
    val interestRate: Float = 0.0f
    ) {

    // Calculate borrowed sum
    val actualBorrowedAmount: Float get() = borrowedAmount - deposit

    // Calculate monthly payment
    fun calculateMonthlyPayment(): Float {
        val monthlyInterestRate = interestRate / (12 * 100)
        val totalMonths = duration * 12
        return (actualBorrowedAmount * (monthlyInterestRate * (1 + monthlyInterestRate).pow(totalMonths))) / ((1 + monthlyInterestRate).pow(totalMonths) - 1)
    }

    fun calculateTotalCost(): Float {
        return calculateMonthlyPayment() * (duration * 12)
    }

    // Calculate sum of all interests
    fun calculateSumOfInterests(): Float {
        return calculateTotalCost() - actualBorrowedAmount
    }

}