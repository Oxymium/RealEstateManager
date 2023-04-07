package com.oxymium.realestatemanager.viewmodel

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun getTodayDateTest(){
        val dateFormat: DateFormat = SimpleDateFormat("yyyy/MM/dd")
    }

    // TODO reverse test
    @Test
    fun convertDollarToEuroTest(){
        val dollars = 1
        val euros = 0.812
        val rate = 0.812

        assertEquals(euros, dollars * rate, 0.2)
    }


}