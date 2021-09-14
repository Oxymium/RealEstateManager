package com.oxymium.realestatemanager.features.estates

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.database.EstatesApplication
import com.oxymium.realestatemanager.databinding.FragmentDetailsBinding
import com.oxymium.realestatemanager.features.map.GeoCoderUtils
import com.oxymium.realestatemanager.utils.DateUtils
import com.oxymium.realestatemanager.utils.PictureListener
import com.oxymium.realestatemanager.viewmodel.EstateViewModel
import com.oxymium.realestatemanager.viewmodel.EstateViewModelFactory
import java.util.*


// ---------------
// DetailsFragment
// ---------------

class DetailsFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentDetailsBinding: FragmentDetailsBinding
    private val binding get() = fragmentDetailsBinding

    // EstateViewModel
    private val estateViewModel: EstateViewModel by activityViewModels() {
        EstateViewModelFactory((activity?.application as EstatesApplication).repository,
            (activity?.application as EstatesApplication).repository2)
    }

    // RecyclerView Adapter
    private lateinit var detailsPictureAdapter: DetailsPictureAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(fragmentTAG, "onCreate: ")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)

        fragmentDetailsBinding.lifecycleOwner = activity
        fragmentDetailsBinding.estateViewModel = estateViewModel
        fragmentDetailsBinding.include1.estateViewModel = estateViewModel
        fragmentDetailsBinding.include2.estateViewModel = estateViewModel
        fragmentDetailsBinding.include3.estateViewModel = estateViewModel
        fragmentDetailsBinding.include4.estateViewModel = estateViewModel
        fragmentDetailsBinding.include5.estateViewModel = estateViewModel

        // RecyclerView setup
        val gridLayoutManager = GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
        fragmentDetailsBinding.include3.fragmentDetailsEstatePicturesRecyclerView.layoutManager = gridLayoutManager

        // Observe picture list
        estateViewModel.allPicturesForCurrentEstate.observe(viewLifecycleOwner,
            { detailsPictureAdapter.submitList(it)
            })

        // Setup adapter
        detailsPictureAdapter = DetailsPictureAdapter(
            PictureListener {
                    picture -> Glide.with(this@DetailsFragment).load(picture.path.toUri()).into(fragmentDetailsBinding.include1.layoutDetailsMainPicture)

            }
        )
        // Load Main Picture in Details
        estateViewModel.selectedEstate.observe(viewLifecycleOwner, {
                selectedEstate ->
            // Check for null selected estate (when app starts on tablet, user won't have selected anything yet)
            if (selectedEstate != null) {
                // Disable sold button if Estate already sold
                if (selectedEstate.wasSold) {
                    fragmentDetailsBinding.layoutDetailsSellButton.visibility = GONE
                }else{
                    fragmentDetailsBinding.layoutDetailsSellButton.visibility = VISIBLE
                }
                Glide.with(this@DetailsFragment)
                    .load(selectedEstate.mainPicturePath)
                    .placeholder(R.drawable.estate_placeholder4)
                    .into(fragmentDetailsBinding.include1.layoutDetailsMainPicture)

                // Static map API call "https://maps.googleapis.com/maps/api/staticmap?center=Annecy+Le+Vieux,Bois+Fleuris,France&zoom=14&size=400x400&key=AIzaSyBfhFAJtJzQmOIRhGuPjuyyThh5CzK_6p8"
                var latLng = GeoCoderUtils().getLatLngFromCompleteAddress(requireActivity(), GeoCoderUtils().fuseAllElementsFromAddress(selectedEstate.address, selectedEstate.zipCode.toString(), selectedEstate.location))

                /* Glide.with(this@DetailsFragment)
                    .load("https://maps.googleapis.com/maps/api/staticmap?center=${latLng.latitude},${latLng.longitude}&zoom=14&size=400x400&key=AIzaSyBfhFAJtJzQmOIRhGuPjuyyThh5CzK_6p8")
                    .placeholder(R.drawable.estate_placeholder4)
                    .into(fragmentDetailsBinding.include5.fragmentDetailsEstateStaticMap) */
            }

        })

        // Observe sale date trigger
        estateViewModel.wasSellButtonClicked.observe(viewLifecycleOwner, {
                sellButtonWasClicked ->
            if (sellButtonWasClicked == 1){
                estateViewModel.wasSellButtonClicked.value = 0
                alertDialogSellingDate()
            }
        })

        // Adapter init
        fragmentDetailsBinding.include3.fragmentDetailsEstatePicturesRecyclerView.adapter = detailsPictureAdapter

        return binding.root

    }

    private fun alertDialogSellingDate() {

        // Get current date
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = DateUtils().getTodayInMillis()

        val newCalendar = Calendar.getInstance()
        newCalendar.timeInMillis = DateUtils().getTodayInMillis()
        var soldDateInMillis: Long? = null

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
        { dialog, which ->
            // Update with selected Date as sold
            soldDateInMillis?.let { estateViewModel.updateEstateIntoDatabase(it) }
        }

        builder.setNegativeButton(
            "Cancel"
        ) { dialog, which -> dialog.cancel() }

        builder.show()
    }
}
