package com.oxymium.realestatemanager.features.estates

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.oxymium.realestatemanager.BuildConfig.MAPS_API_KEY
import com.oxymium.realestatemanager.ENABLE_STATIC_MAP
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.databinding.FragmentDetailsBinding
import com.oxymium.realestatemanager.features.create.NearbyPlaceAdapter
import com.oxymium.realestatemanager.features.create.NearbyPlaceListener
import com.oxymium.realestatemanager.model.toNearbyPlaceList
import com.oxymium.realestatemanager.utils.DateUtils
import com.oxymium.realestatemanager.viewmodel.EstateViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import java.util.Calendar

// ---------------
// DetailsFragment
// ---------------

class DetailsFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentDetailsBinding: FragmentDetailsBinding
    private val binding get() = fragmentDetailsBinding

    // EstateViewModel
    private val estateViewModel: EstateViewModel by activityViewModel<EstateViewModel>()

    // RecyclerView Adapter
    private lateinit var detailsPictureAdapter: DetailsPictureAdapter
    private lateinit var nearbyPlaceAdapter: NearbyPlaceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(fragmentTAG, "onCreate: ")

    }

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)

        fragmentDetailsBinding.estateViewModel = estateViewModel
        fragmentDetailsBinding.navigatorBar.estateViewModel = estateViewModel
        fragmentDetailsBinding.include1.estateViewModel = estateViewModel
        fragmentDetailsBinding.include2.estateViewModel = estateViewModel
        fragmentDetailsBinding.include3.estateViewModel = estateViewModel
        fragmentDetailsBinding.include4.estateViewModel = estateViewModel
        fragmentDetailsBinding.include5.estateViewModel = estateViewModel
        fragmentDetailsBinding.include6.estateViewModel = estateViewModel
        fragmentDetailsBinding.lifecycleOwner = activity

        // Observe Estate ID
        estateViewModel.selectedEstateId.observe(viewLifecycleOwner) {
            it?.let {
                // Query Estate
                estateViewModel.getEstate(it)
                // Query Picture
                estateViewModel.getSecondaryPicturesByEstateId(it)
            }
        }

        // Observe Agent
        estateViewModel.agentId.observe(viewLifecycleOwner) {
            it?.let{
                estateViewModel.getAgentById(it)
            }
        }

        // RecyclerView setup
        // -- Secondary Pictures
        val gridLayoutManager = GridLayoutManager(requireActivity(), 1, GridLayoutManager.HORIZONTAL, false)
        fragmentDetailsBinding.include4.fragmentDetailsEstatePicturesRecyclerView.layoutManager = gridLayoutManager
        // -- Nearby Places
        val gridLayoutManager2 = GridLayoutManager(requireActivity(), 3, GridLayoutManager.VERTICAL, false)
        fragmentDetailsBinding.include5.detailsNearbyPlacesRecyclerView.layoutManager = gridLayoutManager2

        // Observe secondary pictures list
        estateViewModel.secondaryPicturesForCurrentEstate.observe(viewLifecycleOwner){
            detailsPictureAdapter.submitList(it)
        }

        // Observe nearby place list
        estateViewModel.queriedEstate.observe(viewLifecycleOwner) { estate ->
            nearbyPlaceAdapter.submitList(estate.nearbyPlaces?.toNearbyPlaceList())
        }

        // Setup picture adapter
        detailsPictureAdapter = DetailsPictureAdapter(
            // onClick secondary picture
            PictureListener {
                    picture ->
                Glide
                    .with(this@DetailsFragment)
                    .load(picture.path)
                    .placeholder(R.drawable.estate_7)
                    .into(fragmentDetailsBinding.include1.layoutDetailsMainPicture)
            }
        )

        // Setup nearby places adapter
        nearbyPlaceAdapter = NearbyPlaceAdapter(
            NearbyPlaceListener{  } // no need pass anything here since we don't want to be able to click in Details mode
        )

        // Adapter init
        // -- Pictures
        fragmentDetailsBinding.include4.fragmentDetailsEstatePicturesRecyclerView.adapter = detailsPictureAdapter
        // -- Nearby places
        fragmentDetailsBinding.include5.detailsNearbyPlacesRecyclerView.adapter = nearbyPlaceAdapter

        // Load Main Picture in Details
        estateViewModel.queriedEstate.observe(viewLifecycleOwner) { queriedEstate ->
            // Check for null selected estate (when app starts on tablet, user won't have selected anything yet)
            if (queriedEstate != null) {

                Glide
                    .with(this@DetailsFragment)
                    .load(queriedEstate.mainPicturePath)
                    .placeholder(R.drawable.estate_4)
                    .into(fragmentDetailsBinding.include1.layoutDetailsMainPicture)

                // Load static map
                if (ENABLE_STATIC_MAP) {
                    Glide.with(this@DetailsFragment)
                        .load("https://maps.googleapis.com/maps/api/staticmap?center=${queriedEstate.latitude},${queriedEstate.longitude}&zoom=14&size=400x400&key=${MAPS_API_KEY}")
                        .placeholder(R.drawable.estate_4)
                        .into(fragmentDetailsBinding.include6.fragmentDetailsEstateStaticMap)
                }else{
                    Snackbar.make(binding.root, "Static map turned off", Snackbar.LENGTH_LONG).show()
                }

            }

        }

        // Observe sale date trigger
        estateViewModel.wasSellButtonClicked.observe(viewLifecycleOwner) {
            if (it) alertDialogSellingDate()
        }

        return binding.root

    }

    private fun alertDialogSellingDate() {

        // Get current date
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = DateUtils().getTodayInMillis()

        val newCalendar = Calendar.getInstance()
        newCalendar.timeInMillis = DateUtils().getTodayInMillis()
        var soldDateInMillis: Long = newCalendar.timeInMillis

        // Alert Dialog
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        val picker = DatePicker(activity)
        picker.calendarViewShown = false
        // Set max date to prevent setting sold date > today
        picker.maxDate = calendar.timeInMillis
        // Init with today's date & attach listener
        picker.init(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        { view, year, month, day ->
            Log.d(fragmentTAG, "selected date: $day/$month/$year")
            newCalendar.set(Calendar.YEAR, year)
            newCalendar.set(Calendar.MONTH, month)
            newCalendar.set(Calendar.DAY_OF_MONTH, day)
            soldDateInMillis = newCalendar.timeInMillis
        }

        builder.setTitle("Selling date")
        builder.setView(picker)
        builder.setPositiveButton("Save")
        { _, _ ->
            // Update with selected Date as sold
            soldDateInMillis.let { estateViewModel.updateEstateIntoDatabase(it) }
        }

        builder.setNegativeButton(
            "Cancel"
        ) { dialog, which -> dialog.cancel() }

        builder.show()
    }
}

