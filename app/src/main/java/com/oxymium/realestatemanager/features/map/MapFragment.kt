package com.oxymium.realestatemanager.features.map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.database.EstatesApplication
import com.oxymium.realestatemanager.databinding.FragmentMapBinding
import com.oxymium.realestatemanager.model.Estate
import com.oxymium.realestatemanager.viewmodel.EstateViewModel
import com.oxymium.realestatemanager.viewmodel.EstateViewModelFactory
import java.io.IOException

// -----------
// MapFragment
// -----------

class MapFragment: Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private val fragmentTAG = javaClass.simpleName

    lateinit var mapView: MapView

    // Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var previouslyKnownLocation: Location

    // Keys for storing activity state.
    private val KEY_CAMERA_POSITION = "camera_position"
    private val KEY_LOCATION = "location"

    // EstateViewModel
    private val estateViewModel: EstateViewModel by activityViewModels() {
        EstateViewModelFactory((activity?.application as EstatesApplication).repository,(activity?.application as EstatesApplication).repository2 )
    }

    // DataBinding
    private lateinit var fragmentMapBinding: FragmentMapBinding
    private val binding get() = fragmentMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(fragmentTAG, "onCreate: ")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentMapBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)
        mapView = fragmentMapBinding.mapView

        fragmentMapBinding.lifecycleOwner = activity
        fragmentMapBinding.mapViewModel = estateViewModel

        mapView.onCreate(savedInstanceState)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        mapView.getMapAsync(this);

        return binding.root
    }

    // Map Callback
    override fun onMapReady(googleMap: GoogleMap) {

        Log.d(fragmentTAG, "onMapReady: CALLED")


        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        // Enable click listener for markers
        googleMap.setOnMarkerClickListener(this)
        googleMap.isMyLocationEnabled = true
        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                location : Location? ->
            if (location != null) {
                previouslyKnownLocation = location
                val zoomLevel = 13.0f
                val currentPosition = LatLng(previouslyKnownLocation.latitude, previouslyKnownLocation.longitude)
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, zoomLevel))
            }

        }

        // Observe EstateViewModel to get AllEstates
        estateViewModel.allEstates.observe(viewLifecycleOwner,
            { it ->
                it.forEach() {
                    // Try and catch IOException to prevent no corresponding address error
                    try{
                        // Color marker differently depending on sold status RED = SOLD, GREEN = AVAILABLE
                        if (it.wasSold){
                            val marker = googleMap.addMarker(
                                    MarkerOptions()
                                    .position(getLatLngFromCompleteAddress(fuseAllElementsFromAddress(it.address, it.zipCode.toString(), it.location)))
                                    .title(it.id.toString())
                                    .snippet("Type - " + it.type + "\n" + "Price - $" + it.price.toString())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))
                            marker?.tag = it
                        }else{
                            val marker = googleMap
                                .addMarker(
                                MarkerOptions()
                                    .position(getLatLngFromCompleteAddress(fuseAllElementsFromAddress(it.address, it.zipCode.toString(), it.location)))
                                    .title(it.id.toString())
                                    .snippet("Type - " + it.type + "\n" + "Price - $" + it.price.toString())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                            )
                            marker?.tag = it
                        }

                    }catch (e: IOException){
                        println("Address ERROR for Estate number: " +  it.id)
                    }

                }
            })

    }

    // OnClickMarker logic
    override fun onMarkerClick(marker: Marker): Boolean {
        estateViewModel.getEstateFromId(marker.tag as Estate)
        return false
    }

    // FUSE address + zipCode + Location into one String
    private fun fuseAllElementsFromAddress(address: String, postalCode: String, location: String): String {

        return "$address, $postalCode, $location"
    }

    // Get LAT & LNG from Estate address with GEOCODER
    @Throws(IOException::class)
    private fun getLatLngFromCompleteAddress(fusedAddress: String): LatLng {
        val geocoder = Geocoder(requireActivity())
        // 1 result
        val availableAddresses = geocoder.getFromLocationName(fusedAddress, 1)
        val result: Address = availableAddresses[0]

        return LatLng(result.latitude, result.longitude)

    }

    // LifeCycle (required for MapView)
    override fun onResume() {
        mapView.onResume()
        super.onResume()
    }


    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }


}