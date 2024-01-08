package com.oxymium.realestatemanager.model

import org.junit.Test
import kotlin.test.assertEquals


class LoanTest {

    @Test
    fun monthlyPaymentTest() {
        val expectedResult = 354.1186f
        // GIVEN
        val givenLoan = Loan(
            borrowedAmount = 100000f,
            deposit = 30000f,
            duration = 20,
            interestRate = 2f
        )
        // EXPECTED RESULTS
        val monthlyPayment = givenLoan.monthlyPayment()

        // THEN
        assertEquals(expectedResult, monthlyPayment)
    }
}