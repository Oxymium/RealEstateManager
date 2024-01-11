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
        val givenBorrowedAmount = 150000f
        // WHEN
        loanViewModel.updateBorrowedAmount(givenBorrowedAmount)
        val updatedBorrowedAmount = loanViewModel.loan.value
        // THEN
        assertEquals(givenBorrowedAmount, updatedBorrowedAmount?.borrowedAmount)
    }

    @Test
    fun updateDepositTest() {
        // GIVEN
        val givenDeposit = 80000f
        // WHEN
        loanViewModel.updateDeposit(givenDeposit)
        val updatedGivenDeposit = loanViewModel.loan.value
        // THEN
        assertEquals(givenDeposit, updatedGivenDeposit?.deposit)
    }

    @Test
    fun updateDurationTest() {
        // GIVEN
        val givenDuration = 25
        // WHEN
        loanViewModel.updateDuration(givenDuration)
        val updatedGivenDuration = loanViewModel.loan.value
        // THEN
        assertEquals(givenDuration, updatedGivenDuration?.duration)
    }

    @Test
    fun updateInterestRateTest() {
        // GIVEN
        val givenInterestRate = 3.27f
        // WHEN
        loanViewModel.updateInterestRate(givenInterestRate)
        val updatedGivenInterestRate = loanViewModel.loan.value
        // THEN
        assertEquals(givenInterestRate, updatedGivenInterestRate?.interestRate)
    }

}