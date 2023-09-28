package com.oxymium.realestatemanager.features.map

import android.location.Address
import android.location.Geocoder
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.model.LatLng

// --------------
// GeoCoderUtils
// --------------

class GeoCoderUtils {

    // FUSE address + zipCode + Location into one String
    fun fuseAllElementsFromAddress(address: String, postalCode: String, location: String): String{
        return "$address, $postalCode, $location"
    }

    // Get LAT & LNG from Estate address with GEOCODER
    //@Throws(IOException::class)
    fun getLatLngFromCompleteAddress(fragmentActivity: FragmentActivity, fusedAddress: String?): LatLng {

        val geocoder = Geocoder(fragmentActivity)
        // 1 result
        val availableAddresses = try { fusedAddress?.let { geocoder.getFromLocationName(it, 1) }

        }catch (e: java.lang.Exception){
            null
        }
        val result: Address?
        return if (!availableAddresses.isNullOrEmpty()){
            result = availableAddresses[0]
            LatLng(result.latitude, result.longitude)
        }else{
            LatLng(0.0, 0.0)
        }
    }

}