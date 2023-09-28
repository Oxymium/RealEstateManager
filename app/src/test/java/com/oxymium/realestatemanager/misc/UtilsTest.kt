package com.oxymium.realestatemanager.misc

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date

class UtilsTest {

    // CURRENCY CONVERSION TESTING
    @Test
    fun convertDollarToEuroTest(){
        // GIVEN
        val givenDollars = 10
        val expectedEuros = 8
        // WHEN
        val result = Utils.convertDollarToEuro(givenDollars)
        // THEN
        assertEquals(expectedEuros, result)
    }

    @Test
    fun convertEuroToDollarTest(){
        // GIVEN
        val givenEuros = 10
        val expectedDollars = 12
        // WHEN
        val result = Utils.convertEuroToDollar(givenEuros)
        // THEN
        assertEquals(expectedDollars, result)
    }

    // CALENDAR TESTING
    @Test
    fun getTodayDateTest(){
        // GIVEN
        val expectedFormat = "yyyy/MM/dd"
        val expectedDate = SimpleDateFormat(expectedFormat).format(Date())
        // WHEN
        val result = Utils.getTodayDate()
        // THEN
        assertEquals(expectedDate, result)
    }

    @Test
    fun getTodayDateNewFormat(){
        // GIVEN
        val expectedFormat = "dd/MM/yyyy"
        val expectedDate = SimpleDateFormat(expectedFormat).format(Date())
        // WHEN
        val result = Utils.getTodayDateNewFormat()
        // THEN
        assertEquals(expectedDate, result)
    }

    // CONNECTIVITY TESTING
    @Test
    fun isInternetAvailableTest(){
        // GIVEN
        val context = mockk<Context>()
        val wifiManager = mockk<WifiManager>()
        every { context.getSystemService(Context.WIFI_SERVICE) } returns wifiManager

        var expectedResult: Boolean?

        // WIFI ENABLED
        every { wifiManager.isWifiEnabled } returns true
        expectedResult = true

        // WHEN
        val resultTrue = Utils.isInternetAvailable(context)

        // THEN
        assertEquals(expectedResult, resultTrue)

        // WIFI DISABLED
        every { wifiManager.isWifiEnabled } returns false
        expectedResult = false

        // WHEN
        val resultFalse = Utils.isInternetAvailable(context)

        // THEN
        assertEquals(expectedResult, resultFalse)
    }

    @Test
    fun isInternetAvailableImprovedTest(){
        // GIVEN
        val context = mockk<Context>()
        val connectivityManager = mockk<ConnectivityManager>()
        val networkInfo = mockk<NetworkInfo>()

        every { context.getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager
        every { connectivityManager.activeNetworkInfo } returns networkInfo
        every { networkInfo.isConnected } returns true

        val expectedResult = true

        // WHEN
        val result = Utils.isInternetAvailableImproved(context)

        // THEN
        assertEquals(expectedResult, result)
    }

}