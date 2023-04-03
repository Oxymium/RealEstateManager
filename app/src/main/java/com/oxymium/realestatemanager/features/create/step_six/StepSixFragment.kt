package com.oxymium.realestatemanager.features.create.step_six

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
import androidx.recyclerview.widget.GridLayoutManager
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.database.EstatesApplication
import com.oxymium.realestatemanager.databinding.FragmentStepSixBinding
import com.oxymium.realestatemanager.features.create.CreateViewModel
import com.oxymium.realestatemanager.features.create.step_one.LabelAdapter
import com.oxymium.realestatemanager.utils.LabelListener
import com.oxymium.realestatemanager.viewmodel.CreateViewModelFactory

// ----------------
// StepSixFragment
// ----------------

class StepSixFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var stepSixBinding: FragmentStepSixBinding
    private val binding get() = stepSixBinding

    // RecyclerView
    private lateinit var labelAdapter: LabelAdapter

    private val createViewModel: CreateViewModel by activityViewModels() {
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

        stepSixBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_step_six, container, false)
        stepSixBinding.lifecycleOwner = activity
        stepSixBinding.createViewModel = createViewModel

        // RecyclerView setup
        val gridLayoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        stepSixBinding.stepSixNearbyPlacesRecyclerView.layoutManager = gridLayoutManager

        // Submit Nearby Places list
        createViewModel.nearbyPlaces.observe(viewLifecycleOwner){
            labelAdapter.submitList(it)
        }

        labelAdapter = LabelAdapter(
            LabelListener {
                // Update selected Label
                // TODO handle NearbyPlaces logic
                createViewModel.updateSelectedNearbyPlaces(it.label)
            }
        )

        createViewModel.selectedNearbyPlaces.observe(viewLifecycleOwner){
                selectedLabels ->
                createViewModel.nearbyPlaces.value?.map { label ->
                    if (selectedLabels.contains(label.label)) {
                        label.copy(isSelected = true)
                    } else {
                        label.copy(isSelected = false)
                    }
                }?.let {
                    createViewModel.updateSelectedPlaces(it)
                }
        }

        // Adapter init
        stepSixBinding.stepSixNearbyPlacesRecyclerView.adapter = labelAdapter

        // --------------
        // TEXT WATCHERS
        // --------------

        // STREET
        stepSixBinding.stepSixStreetInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) createViewModel.updateStreet(s.toString())
                    else createViewModel.updateStreet(null)
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        // ZIP CODE
        stepSixBinding.stepSixZipcodeInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) createViewModel.updateZipCode(s.toString())
                    else createViewModel.updateZipCode(null)
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        // LOCATION
        stepSixBinding.stepSixLocationInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) createViewModel.updateLocation(s.toString().replaceFirstChar { it.uppercaseChar() })
                    else createViewModel.updateLocation(null)
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        return binding.root

    }
}