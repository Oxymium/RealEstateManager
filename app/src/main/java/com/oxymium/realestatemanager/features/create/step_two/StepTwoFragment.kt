package com.oxymium.realestatemanager.features.create.step_two

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
import com.oxymium.realestatemanager.databinding.FragmentStepTwoBinding
import com.oxymium.realestatemanager.features.create.CreateViewModel
import com.oxymium.realestatemanager.viewmodel.CreateViewModelFactory

// ---------------
// StepTwoFragment
// ---------------

class StepTwoFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var stepTwoBinding: FragmentStepTwoBinding
    private val binding get() = stepTwoBinding

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

        stepTwoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_step_two, container, false)
        stepTwoBinding.lifecycleOwner = activity
        stepTwoBinding.createViewModel = createViewModel

        // -------------
        // TEXT WATCHERS
        // -------------

        // PRICE
        stepTwoBinding.stepTwoPriceInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) createViewModel.updatePrice(s.toString().toInt())
                    else createViewModel.updatePrice(null)
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        // SURFACE
        stepTwoBinding.stepTwoSurfaceInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) createViewModel.updateSurface(s.toString().toInt())
                    else createViewModel.updateSurface(null)
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        // ROOMS
        stepTwoBinding.stepTwoRoomsInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.toString().startsWith("0") && s.length > 1){
                        stepTwoBinding.stepTwoRoomsInput.setText("0")
                    }
                    if (s.isNotEmpty()) createViewModel.updateRooms(s.toString().toInt())
                    else createViewModel.updateRooms(null)
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        // BEDROOMS
        stepTwoBinding.stepTwoBedroomsInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) createViewModel.updateBedrooms(s.toString().toInt())
                    else createViewModel.updateBedrooms(null)
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        // BATHROOMS
        stepTwoBinding.stepTwoBathroomsInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) createViewModel.updateBathrooms(s.toString().toInt())
                    else createViewModel.updateBathrooms(null)
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        // BATHROOMS
        stepTwoBinding.stepTwoEnergyScoreInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) createViewModel.updateEnergyScore(s.toString().toInt())
                    else createViewModel.updateEnergyScore(null)
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        return binding.root
    }

}