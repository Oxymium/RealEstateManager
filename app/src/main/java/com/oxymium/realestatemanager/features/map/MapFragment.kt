package com.oxymium.realestatemanager.features.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import com.google.maps.android.clustering.ClusterManager
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.database.EstatesApplication
import com.oxymium.realestatemanager.databinding.FragmentMapBinding
import com.oxymium.realestatemanager.model.ClusteredEstate
import com.oxymium.realestatemanager.model.Estate
import com.oxymium.realestatemanager.model.LatLngZoom
import com.oxymium.realestatemanager.viewmodel.EstateViewModel
import com.oxymium.realestatemanager.viewmodel.EstateViewModelFactory
import com.oxymium.realestatemanager.viewmodel.MapSelectedViewModel

// -----------
// MapFragment
// -----------

// SHARED PREFERENCES VALUES ----------------------
const val MAP_SHARED_PREFERENCES = "MAP_PREFERENCES"
const val LATITUDE_SHARED_PREFERENCES = "LATITUDE"
const val LONGITUDE_SHARED_PREFERENCES = "LONGITUDE"
const val ZOOM_SHARED_PREFERENCES = "ZOOM_LEVEL"
// ------------------------------------------------
class MapFragment : Fragment(), OnMapReadyCallback{

    private val fragmentTAG = javaClass.simpleName

    // ViewModel
    private val mapSelectedViewModel: MapSelectedViewModel by activityViewModels()

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var clusterManager: ClusterManager<ClusteredEstate>

    // Permission
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                enableMyLocation()
                // Cluster Manager
                handleMapReady(googleMap)
            }
        }

    // Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var previouslyKnownLocation: Location

    // EstateViewModel
    private val estateViewModel: EstateViewModel by activityViewModels{
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
    ): View {

        fragmentMapBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)
        mapView = fragmentMapBinding.mapView

        fragmentMapBinding.lifecycleOwner = activity
        fragmentMapBinding.mapViewModel = estateViewModel

        mapView.onCreate(savedInstanceState)

        // Fused Location provider
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        mapView.getMapAsync(this)

        return binding.root
    }

    // Map Callback
    override fun onMapReady(googleMap: GoogleMap) {
        Log.d(fragmentTAG, "onMapReady: CALLED")

        this.googleMap = googleMap
        clusterManager = ClusterManager<ClusteredEstate>(activity, googleMap)

        requestLocationPermission()
        disableWatermark()

        // Observe estate list
        observeEstates()

        // Retrieve last camera's know position
        val latLngZoom = retrieveSharedPreferences()
        if (latLngZoom.latitude != 0.0 && latLngZoom.longitude != 0.0) moveCameraToLocation(latLngZoom)
    }

    private fun saveSharedPreferences(latitude: Double, longitude: Double, zoomLevel: Float){
        val sharedPreferences = context?.getSharedPreferences(MAP_SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putString(LATITUDE_SHARED_PREFERENCES, latitude.toString())
        editor?.putString(LONGITUDE_SHARED_PREFERENCES, longitude.toString())
        editor?.putFloat(ZOOM_SHARED_PREFERENCES, zoomLevel)
        editor?.apply()
    }

    private fun retrieveSharedPreferences(): LatLngZoom {
        val sharedPreferences = context?.getSharedPreferences(MAP_SHARED_PREFERENCES, Context.MODE_PRIVATE)
        // LAT
        val retrievedLatitude = sharedPreferences?.getString(LATITUDE_SHARED_PREFERENCES, "0.0")
        val retrievedLatitudeDouble = retrievedLatitude?.toDoubleOrNull() ?: 0.0
        // LNG
        val retrievedLongitude = sharedPreferences?.getString(LONGITUDE_SHARED_PREFERENCES, "0.0")
        val retrievedLongitudeDouble = retrievedLongitude?.toDoubleOrNull() ?: 0.0
        // ZOOM
        val retrievedZoomLevel = sharedPreferences?.getFloat(ZOOM_SHARED_PREFERENCES, 0.0f) ?: 0.0f

        return LatLngZoom(retrievedLatitudeDouble, retrievedLongitudeDouble, retrievedZoomLevel)
    }

    private fun moveCameraToLocation(latLngZoom: LatLngZoom) {
        val cameraPosition = CameraPosition.Builder()
            .target(LatLng(latLngZoom.latitude, latLngZoom.longitude))
            .zoom(latLngZoom.zoom)
            .build()
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        //googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    // LOCATION PERMISSION
    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            enableMyLocation()
        }
    }

    // USER'S LOCATION
    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap.isMyLocationEnabled = true
        } else {
            Snackbar.make(binding.root, "PERMISSION NOT GRANTED", Snackbar.LENGTH_LONG).show()
        }
    }

    // HANDLE MAP READY
    private fun handleMapReady(googleMap: GoogleMap) {
        // Enable user location
        enableMyLocation()
        // Check if Permission are granted
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
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
        }
    }

    private fun disableWatermark(){
        val googleLogo = mapView.findViewWithTag<View>("GoogleWatermark")
        googleLogo.visibility = View.GONE
    }

    // Observer on All Estates
    private fun observeEstates(){
        estateViewModel.allEstates.observe(viewLifecycleOwner){
            generateAndColorMarkers(it)
        }
    }

    @SuppressLint("PotentialBehaviorOverride")
    private fun generateAndColorMarkers(estates: List<Estate>){

        estates.forEach {
            // CLUSTER THE ESTATES
            val cluster = ClusteredEstate(it)
            clusterManager.addItem(cluster)
        }

        clusterManager.renderer = EstateClusterRenderer(requireContext(), googleMap, clusterManager)
        clusterManager.setOnClusterItemClickListener{
            // Provide selected ClusteredEstate to ViewModel
            it.estate.id?.let { id -> mapSelectedViewModel.getSelectedEstate(id) }
            true
        }
        googleMap.setOnMarkerClickListener(clusterManager.markerManager)
        googleMap.setOnCameraIdleListener(clusterManager)
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
        // Save previously known camera position
        saveSharedPreferences(
            googleMap.cameraPosition.target.latitude,
            googleMap.cameraPosition.target.longitude,
            googleMap.cameraPosition.zoom
            )
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

}