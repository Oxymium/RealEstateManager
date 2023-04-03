package com.oxymium.realestatemanager.features.map

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.model.LatLng
import com.oxymium.realestatemanager.model.Estate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

// --------------
// GeoCoderUtils
// --------------

class GeoCoderUtils() {

    // FUSE address + zipCode + Location into one String
    fun fuseAllElementsFromAddress(address: String, postalCode: String, location: String): String{
        return "$address, $postalCode, $location"
    }

    fun Estate.fuseAddressElements(street: String, zipCode: String, location: String): String{
        return "$street, $zipCode, $location"
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun flowGeocodeTest(context: Context, fusedAddress: String, maxResults: Int): Flow<List<Address>> = callbackFlow {
        val geocoder = Geocoder(context)
        val listener = object : Geocoder.GeocodeListener{
            override fun onGeocode(addresses: MutableList<Address>) {
                try {
                    if (!isClosedForSend) trySend(addresses).isSuccess
                }catch (e: Exception){
                    close(e)
                }
            }
            override fun onError(errorMessage: String?) {
                // Handle error
            }
        }
        geocoder.getFromLocationName(fusedAddress, 1, listener)
        awaitClose()
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
        var result: Address? = null
        return if (!availableAddresses.isNullOrEmpty()){
            result = availableAddresses[0]
            LatLng(result.latitude, result.longitude)
        }else{
            LatLng(0.0, 0.0)
        }
    }

}