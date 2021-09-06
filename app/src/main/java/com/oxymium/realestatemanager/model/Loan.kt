package com.oxymium.realestatemanager.model

data class Loan(
    var borrowedAmount:Float,
    var deposit: Float,
    var duration: Int,
    var interestRate: Float)