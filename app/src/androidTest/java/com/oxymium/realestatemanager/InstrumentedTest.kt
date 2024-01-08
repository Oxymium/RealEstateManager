package com.oxymium.realestatemanager

import android.content.Context
import android.net.wifi.WifiManager
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.oxymium.realestatemanager.misc.Utils
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InstrumentedTest {

    @Test
    @Throws(Exception::class)
    fun testNetworkAvailability() {
        // Context of the app under test.
        val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext

        // Desactivate WIFI
        val wifiManager = appContext.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifiManager.isWifiEnabled = false

        // Check if Network is available
        Thread.sleep(5000)
        var isNetworkAvailable: Boolean = Utils.isInternetAvailable(appContext)
        println(isNetworkAvailable)
        assertFalse(isNetworkAvailable)

        // Activate WIFI
        wifiManager.isWifiEnabled = true
        Thread.sleep(5000)
        isNetworkAvailable = Utils.isInternetAvailable(appContext)
        println(isNetworkAvailable)
        assertTrue(isNetworkAvailable)
    }

    @Test
    @Throws(Exception::class)
    fun testNetworkAvailability2() {
        val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext

        // Desactivate WIFI
        val wifiManager = appContext.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifiManager.isWifiEnabled = false

        // Desactivate 4G
        /*TelephonyManager telephonyManager = (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE);
    Method dataClass = telephonyManager.getClass().getDeclaredMethod("setDataEnabled", boolean.class);
    dataClass.invoke(telephonyManager, false); */

        // Check if Network is available
      //  Thread.sleep(5000)
      //  val isNetworkAvailable2: Boolean = Utils.isInternetAvailable2(appContext)
      //  println(isNetworkAvailable2)
      //  assertFalse(isNetworkAvailable2)

        // Activate WIFI
        /*wifiManager.setWifiEnabled(true);
    Thread.sleep(5000);
    isNetworkAvailable2 = Utils.isInternetAvailable2(appContext);
    System.out.println(isNetworkAvailable2);
    assertTrue(isNetworkAvailable2); */
    }
}