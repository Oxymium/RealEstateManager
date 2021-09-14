package com.oxymium.realestatemanager.database

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.annotation.WorkerThread
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ReportFragment
import androidx.sqlite.db.SimpleSQLiteQuery
import com.google.android.gms.maps.model.LatLng
import com.oxymium.realestatemanager.misc.Utils
import com.oxymium.realestatemanager.model.Estate
import com.oxymium.realestatemanager.utils.DateUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.Math.random

// ----------------
// EstateRepository
// ----------------

class EstateRepository(private val estateDao: EstateDao) {

    // Get allEstates from the DB as Flow
    val allEstates: Flow<List<Estate>> = estateDao.getLocalisedEstate()

    fun getSearchedEstates(search: SimpleSQLiteQuery): Flow<List<Estate>> {
        return estateDao.getSearchedEstates(search)
    }

    suspend fun generateAddress(fragmentActivity: FragmentActivity, address: String, zipCode: String, location: String): LatLng?{

        var latLngResult: LatLng? = null

        return withContext(Dispatchers.IO) {
            val result: Address
            try {
                val availableAddresses =
                    Geocoder(fragmentActivity).getFromLocationName(
                        "$address, $zipCode, $location",
                        0
                    )
                if (!availableAddresses.isNullOrEmpty()) {
                    result = availableAddresses[0]
                    latLngResult = LatLng(result.latitude, result.longitude)
                } else {
                    latLngResult = LatLng(0.0, 0.0)
                }
            } catch (e: IOException) {
            }

            return@withContext latLngResult
        }
    }

    // Insert Estate into DB
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(estate: Estate): Long {
        return estateDao.insert(estate)
    }

    // Delete Estate from DB (mainly for testing purposes)
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll() {
        estateDao.deleteAll()
    }

    // Update Estate in DB
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateEstate(estate: Estate) {
        estateDao.update(estate)
    }

    // Random for testing purposes
    fun provideRandomEstate(): Estate {

        val soldStatus = arrayOf(true, false)
        val randSoldStatus = soldStatus.random()

        val types = arrayOf(
            "Bungalow",
            "Condominium",
            "Cottage",
            "Duplex",
            "Farmhouse",
            "Flat",
            "Lodge",
            "Manor",
            "Penthouse",
            "Townhouse",
            "Villa"
        )
        val randType = types.random()

        val prices = arrayOf(100000, 150000, 550200, 660500, 1000000, 2000000, 2500000)
        val randPrice = prices.random()


        val energies = arrayOf("A+", "A", "B", "C", "D")
        val randEnergy = energies.random()

        val randSurface = (0..200).random()

        val randRooms = (1..20).random()
        val randBedrooms = (1..15).random()
        val randBathrooms = (1..10).random()


        val addresses = arrayOf("21 rue de la Paix", "19 rue des Tulipes", "07 avenue des bois fleuris")
        val randAddress = addresses.random()
        val randZipCode = (10000..99999).random()
        val locations = arrayOf("New-York", "Paris", "Tokyo", "Singapore", "Moscow", "Seoul", "Sidney")
        val randLocation = locations.random()

        val highSpeedInternet = arrayOf(true, false)
        val randHighSpeedInternet = highSpeedInternet.random()

        val nearbyPlace = arrayOf("Coffee", "Mall", "Swimming pool", "Cinema", "Park", "School", "Restaurant", "Lake", "Forest")
        val randNearbyPlaces = nearbyPlace.random() + " " + nearbyPlace.random() + " " + nearbyPlace.random()

        val description = "A very cosy place near the suburb area. Pretty new and comfy!"

        val mainPictures = arrayOf(
            "android.resource://com.oxymium.realestatemanager/drawable/estate_placeholder2",
            "android.resource://com.oxymium.realestatemanager/drawable/estate_placeholder3",
            "android.resource://com.oxymium.realestatemanager/drawable/estate_placeholder4",
            "android.resource://com.oxymium.realestatemanager/drawable/estate_placeholder5",
            "android.resource://com.oxymium.realestatemanager/drawable/estate_placeholder6",
            "android.resource://com.oxymium.realestatemanager/drawable/estate_placeholder7",
            "android.resource://com.oxymium.realestatemanager/drawable/estate_placeholder8",
            "android.resource://com.oxymium.realestatemanager/drawable/estate_placeholder9",
        )
        val randMainPicture = mainPictures.random()

        val agent = arrayOf("Agent X", "Agent Y", "Agent Z")
        val randAgent = agent.random()

        return Estate(1631085363547, randSoldStatus, DateUtils().getTodayInMillis(),
            randType, randPrice, randEnergy, randSurface, randRooms, randBedrooms, randBathrooms, randAddress,
            randZipCode, randLocation, randHighSpeedInternet, randNearbyPlaces, description, randMainPicture, randAgent)
    }


}