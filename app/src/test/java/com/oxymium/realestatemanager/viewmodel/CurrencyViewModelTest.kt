package com.oxymium.realestatemanager.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

class CurrencyViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val currencyViewModel = CurrencyViewModel()

    @Test
    fun updateExchangeRateTest() {
        // Given
        val givenExchangeRate = 4.0
        // When
        currencyViewModel.updateExchangeRate(givenExchangeRate)
        val result = currencyViewModel.currencyConversion
        // Then
        assertEquals(result.value?.exchangeRate, givenExchangeRate)
    }

    @Test
    fun updateInputAmountTest() {
        // Given
        val givenInputAmount = 100000.00
        // When
        currencyViewModel.updateInputAmount(givenInputAmount)
        val result = currencyViewModel.currencyConversion
        // Then
        assertEquals(result.value?.input, givenInputAmount)
    }

}