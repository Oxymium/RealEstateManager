package com.oxymium.realestatemanager.model

import org.junit.Test
import kotlin.test.assertEquals

class EnergyTest {

    @Test
    fun getEnergyRatingTest() {
        // GIVEN
        val givenEnergyScore = 200 // should be 151 - 230 => RatingD
        val givenEnergyRating = Energy.RatingD().rating
        // WHEN
        val energyRating = getEnergyRating(givenEnergyScore)
        // THEN
        assertEquals(givenEnergyRating, energyRating)
    }
}