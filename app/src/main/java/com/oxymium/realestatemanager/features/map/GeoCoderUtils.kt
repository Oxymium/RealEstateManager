package com.oxymium.realestatemanager.features.map

import android.app.Activity
import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import kotlin.coroutines.coroutineContext

class GeoCoderUtils() {

    suspend fun coroutineTest(fragmentActivity: FragmentActivity, fusedAddress: String?){
        return withContext(Dispatchers.IO){
            val result = getLatLngFromCompleteAddress(fragmentActivity, fusedAddress)
        }
    }

    // FUSE address + zipCode + Location into one String
    fun fuseAllElementsFromAddress(address: String, postalCode: String, location: String): String{
            return "$address, $postalCode, $location"
        }

    // Get LAT & LNG from Estate address with GEOCODER
    //@Throws(IOException::class)
    fun getLatLngFromCompleteAddress(fragmentActivity: FragmentActivity, fusedAddress: String?): LatLng {

                val geocoder = Geocoder(fragmentActivity)
                // 1 result
                val availableAddresses = geocoder.getFromLocationName(fusedAddress, 1)
                var result: Address? = null
                return if (!availableAddresses.isNullOrEmpty()){
                    result = availableAddresses[0]
                    LatLng(result.latitude, result.longitude)
                }else{
                    LatLng(0.0, 0.0)
                }
            }

}