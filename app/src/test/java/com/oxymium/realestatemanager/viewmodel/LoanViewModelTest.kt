package com.oxymium.realestatemanager.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

class LoanViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val loanViewModel = LoanViewModel()

    @Test
    fun updateBorrowedAmountTest() {
        // GIVEN
        val loan = loanViewModel.loan.value
        val borrowedAmount = 150000f
        // WHEN
        loanViewModel.updateBorrowedAmount(borrowedAmount)
        // THEN
        assertEquals(borrowedAmount, loan?.borrowedAmount)
    }

    @Test
    fun updateDepositTest() {
        // GIVEN
        val loan = loanViewModel.loan.value
        val deposit = 50000f
        // WHEN
        loanViewModel.updateDeposit(deposit)
        // THEN
        assertEquals(deposit, loan?.deposit)

    }

    @Test
    fun updateDurationTest() {
        // GIVEN
        val loan = loanViewModel.loan.value
        val duration = 10
        // WHEN
        loanViewModel.updateDuration(duration)
        // THEN
        assertEquals(duration, loan?.duration)
    }

    @Test
    fun updateInterestRateTest() {
        // GIVEN
        val loan = loanViewModel.loan.value
        val interestRate = 5f
        // WHEN
        loanViewModel.updateInterestRate(5f)
        // THEN
        assertEquals(interestRate, loan?.interestRate)
    }

}