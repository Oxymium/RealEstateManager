package com.oxymium.realestatemanager.model

import junit.framework.TestCase.assertEquals
import org.junit.Test

class CurrencyConversionTest {

    @Test
    fun convertTest() {
        // GIVEN
        val givenTotal = 70.0
        val givenCurrencyConversion = CurrencyConversion(
            input = 100.00,
            exchangeRate = 0.70
        )
        // WHEN
        givenCurrencyConversion.convert()
        // THEN
        assertEquals(givenTotal, givenCurrencyConversion.result)
    }
}