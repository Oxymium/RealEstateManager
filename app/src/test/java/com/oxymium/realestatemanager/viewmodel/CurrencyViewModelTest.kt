package com.oxymium.realestatemanager.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule

class CurrencyViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val currencyViewModel = CurrencyViewModel()

   //@Test
   //fun updateExchangeRateTest(){

   //    // Given
   //    val givenExchangeRate = 4.0

   //    // When
   //    currencyViewModel.updateExchangeRate(givenExchangeRate)
   //    val resultExchangeRate = currencyViewModel.exchangeRate.value

   //    // Then
   //    assertEquals(resultExchangeRate, givenExchangeRate)
   //}

   //@Test
   //fun updateCurrencyTest(){

   //    // Given
   //    val givenCurrency = 100.00

   //    // When
   //    currencyViewModel.updateCurrency(givenCurrency)
   //    val resultCurrency = currencyViewModel.currency.value

   //    // Then
   //    assertEquals(resultCurrency, givenCurrency)
   //}

   //@Test
   //fun updateResultTest(){

   //    // Given
   //    val givenResult = 84.00

   //    // When
   //    currencyViewModel.updateResult(givenResult)
   //    val result = currencyViewModel.result.value

   //    // Then
   //    assertEquals(result, givenResult)
   //}

}