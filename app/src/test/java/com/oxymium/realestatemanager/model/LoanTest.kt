package com.oxymium.realestatemanager.model

import org.junit.Test
import kotlin.test.assertEquals


class LoanTest {

    // GIVEN
    private val givenLoan = Loan(
        borrowedAmount = 200000f,
        deposit = 80000f,
        duration = 25,
        interestRate = 2.25f
    )

    @Test
    fun calculateMonthlyPaymentTest() {
        val expectedResult = 523.3479f

        //  WHEN
        val monthlyPayment = givenLoan.calculateMonthlyPayment()

        // THEN
        assertEquals(expectedResult, monthlyPayment)
    }

    @Test
    fun calculateTotalCostTest() {
        val expectedResult = 157004.38f

        // WHEN
        val totalCost = givenLoan.calculateTotalCost()

        // THEN
        assertEquals(expectedResult, totalCost)
    }

    @Test
    fun calculateSumOfInterestsTest() {
        val expectedSumOfInterests = 37004.375f

        // WHEN
        val sumOfInterests = givenLoan.calculateSumOfInterests()

        // THEN
        assertEquals(expectedSumOfInterests, sumOfInterests)
    }

}