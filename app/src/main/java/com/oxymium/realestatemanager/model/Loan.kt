package com.oxymium.realestatemanager.model

// -----
// Loan
// -----

data class Loan(
    var borrowedAmount:Float,
    var deposit: Float,
    var duration: Int,
    var interestRate: Float)