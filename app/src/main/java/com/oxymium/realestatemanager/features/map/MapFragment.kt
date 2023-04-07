package com.oxymium.realestatemanager.features.map

import android.Manifest
import android.content.pm.PackageManager
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
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.database.EstatesApplication
import com.oxymium.realestatemanager.databinding.FragmentMapBinding
import com.oxymium.realestatemanager.viewmodel.EstateViewModel
import com.oxymium.realestatemanager.viewmodel.EstateViewModelFactory

// -----------
// MapFragment
// -----------

class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private val fragmentTAG = javaClass.simpleName

    private lateinit var mapView: MapView

    // Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var previouslyKnownLocation: Location

    // Keys for storing activity state.
    private val KEY_CAMERA_POSITION = "camera_position"
    private val KEY_LOCATION = "location"

    // EstateViewModel
    private val estateViewModel: EstateViewModel by activityViewModels() {
        EstateViewModelFactory(
            (activity?.application as EstatesApplication).repository3,
            (activity?.application as EstatesApplication).repository,
            (activity?.application as EstatesApplication).repository2
        )
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

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        mapView.getMapAsync(this)

        return binding.root
    }

    // Map Callback
    override fun onMapReady(googleMap: GoogleMap) {
        Log.d(fragmentTAG, "onMapReady: CALLED")

        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION
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
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                previouslyKnownLocation = location
                val zoomLevel = 13.0f
                val currentPosition =
                    LatLng(previouslyKnownLocation.latitude, previouslyKnownLocation.longitude)
                googleMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        currentPosition,
                        zoomLevel
                    )
                )
            }

        }

        // Observe EstateViewModel to get AllEstates
        estateViewModel.allEstates.observe(viewLifecycleOwner){
                it ->
            it.forEach() {

                        // Try and catch IOException to prevent no corresponding address error
                        // Color marker differently depending on sold status RED = SOLD, GREEN = AVAILABLE
                        val defaultMarker: BitmapDescriptor = if (it.wasSold) {
                            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                        } else {
                            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                        }

                        val latLng: LatLng = GeoCoderUtils().getLatLngFromCompleteAddress(
                            requireActivity(),
                            GeoCoderUtils().fuseAllElementsFromAddress(
                                it.street,
                                it.zipCode,
                                it.location
                            )
                        )
//
                        // Check for non-null latLng (= GeoCoder failed to provide coordinates from address)
                        if (latLng.latitude != 0.0 && latLng.longitude != 0.0) {
                            val marker = googleMap.addMarker(
                                MarkerOptions()
                                    .position(latLng)
                                    .title(it.id.toString())
                                    .snippet("Type - " + it.type + "\n" + "Price - $" + it.price.toString())
                                    .icon(defaultMarker)
                            )
//
                            marker?.tag = it
                        } else {
                            println("EMPTY ADDRESS for Estate number: " + it.id)
//
                        }
            }
        }
    }

    // OnClickMarker logic
    override fun onMarkerClick(marker: Marker): Boolean {
        // TODO REPLACE ESTATE CALL
        //estateViewModel.getEstateFromId(marker.tag as Estate)
        return false
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