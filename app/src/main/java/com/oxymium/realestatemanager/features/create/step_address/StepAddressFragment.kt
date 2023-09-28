package com.oxymium.realestatemanager.features.create.step_address

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.database.EstatesApplication
import com.oxymium.realestatemanager.databinding.FragmentStepAddressBinding
import com.oxymium.realestatemanager.features.create.CreateViewModel
import com.oxymium.realestatemanager.features.map.GeoCoderUtils
import com.oxymium.realestatemanager.model.EstateField
import com.oxymium.realestatemanager.viewmodel.CreateViewModelFactory

// ----------------
// StepSixFragment
// ----------------

class StepAddressFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var stepAddressBinding: FragmentStepAddressBinding
    private val binding get() = stepAddressBinding

    private val createViewModel: CreateViewModel by activityViewModels {
        CreateViewModelFactory(
            (activity?.application as EstatesApplication).repository3,
            (activity?.application as EstatesApplication).repository,
            (activity?.application as EstatesApplication).repository2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(fragmentTAG, "onCreate: ")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        stepAddressBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_step_address, container, false)
        stepAddressBinding.lifecycleOwner = activity
        stepAddressBinding.createViewModel = createViewModel

        // --------------
        // TEXT WATCHERS
        // --------------

        // STREET
        stepAddressBinding.stepSixStreetInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) {
                        createViewModel.updateEstateField(EstateField.Street(s.toString()))
                        if (createViewModel.estate.value?.street.isNullOrEmpty() || createViewModel.estate.value?.location.isNullOrEmpty() || createViewModel.estate.value?.zipCode.isNullOrEmpty()) createViewModel.updateEnableReverseGeoCoding(false)
                        else createViewModel.updateEnableReverseGeoCoding(true)
                    }
                    else createViewModel.updateEstateField(EstateField.Street(null))
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        // ZIP CODE
        stepAddressBinding.stepSixZipcodeInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) {
                        createViewModel.updateEstateField(EstateField.ZipCode(s.toString()))
                        if (createViewModel.estate.value?.street.isNullOrEmpty() || createViewModel.estate.value?.location.isNullOrEmpty() || createViewModel.estate.value?.zipCode.isNullOrEmpty()) createViewModel.updateEnableReverseGeoCoding(false)
                        else createViewModel.updateEnableReverseGeoCoding(true)
                    }
                    else createViewModel.updateEstateField(EstateField.ZipCode(null))
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        // LOCATION
        stepAddressBinding.stepSixLocationInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) {
                        createViewModel.updateEstateField(EstateField.Location(s.toString().replaceFirstChar { it.uppercaseChar() }))
                        if (createViewModel.estate.value?.street.isNullOrEmpty() || createViewModel.estate.value?.location.isNullOrEmpty() || createViewModel.estate.value?.zipCode.isNullOrEmpty()) createViewModel.updateEnableReverseGeoCoding(false)
                        else createViewModel.updateEnableReverseGeoCoding(true)
                    }
                    else createViewModel.updateEstateField(EstateField.Location(null))
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        // LATITUDE
        stepAddressBinding.stepSixLatitudeInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) createViewModel.updateEstateField(EstateField.Latitude(s.toString().toDouble()))
                    else createViewModel.updateEstateField(EstateField.Latitude(null))
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        // LONGITUDE
        stepAddressBinding.stepSixLongitudeInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) createViewModel.updateEstateField(EstateField.Longitude(s.toString().toDouble()))
                    else createViewModel.updateEstateField(EstateField.Longitude(null))
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        // Observer for address (fetch LatLng when address complete)
        createViewModel.enableReverseGeoCoding.observe(viewLifecycleOwner){
            if (it == true){
                // Performs the GeoCoding only if the LatLng values are null
                if (createViewModel.editedEstate.value?.latitude == null || createViewModel.editedEstate.value?.longitude == null) {
                    // Fuse all elements of address
                    val fusedAddress = GeoCoderUtils()
                        .fuseAllElementsFromAddress(
                            createViewModel.estate.value?.street ?: "",
                            createViewModel.estate.value?.zipCode ?: "",
                            createViewModel.estate.value?.location ?: ""
                        )

                    val latLngAnswer = GeoCoderUtils().getLatLngFromCompleteAddress(requireActivity(), fusedAddress)
                    createViewModel.updateEstateField(EstateField.Latitude(latLngAnswer.latitude))
                    createViewModel.updateEstateField(EstateField.Longitude(latLngAnswer.longitude))
                }
                // Reset value back to false
                createViewModel.updateEnableReverseGeoCoding(false)
            }
        }

        return binding.root

    }
}