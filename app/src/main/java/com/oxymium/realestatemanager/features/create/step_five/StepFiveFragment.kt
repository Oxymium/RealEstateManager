package com.oxymium.realestatemanager.features.create.step_five

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
import com.oxymium.realestatemanager.databinding.FragmentStepFiveBinding
import com.oxymium.realestatemanager.features.create.CreateViewModel
import com.oxymium.realestatemanager.viewmodel.CreateViewModelFactory

// ----------------
// StepFiveFragment
// ----------------

class StepFiveFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var stepFiveBinding: FragmentStepFiveBinding
    private val binding get() = stepFiveBinding

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

        stepFiveBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_step_five, container, false)
        stepFiveBinding.lifecycleOwner = activity
        stepFiveBinding.createViewModel = createViewModel


        // --------------
        // TEXT WATCHERS
        // --------------

        // DESCRIPTION
        stepFiveBinding.stepFiveDescriptionInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) createViewModel.updateDescription(s.toString())
                    else createViewModel.updateDescription(null)
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        // HIGH-SPEED INTERNET
        stepFiveBinding.stepFiveInternet.setOnCheckedChangeListener {
                _, isChecked ->
            if (isChecked) createViewModel.updateHighSpeedInternet(true)
            else createViewModel.updateHighSpeedInternet(false)
        }

        // FURNISHED
        stepFiveBinding.stepFiveFurnished.setOnCheckedChangeListener {
                _, isChecked ->
            if (isChecked) createViewModel.updateFurnished(true)
            else createViewModel.updateFurnished(false)
        }

        // GARDEN
        stepFiveBinding.stepFiveGarden.setOnCheckedChangeListener {
                _, isChecked ->
            if (isChecked) createViewModel.updateGarden(true)
            else createViewModel.updateGarden(false)
        }

        // DISABLED ACCESSIBILITY
        stepFiveBinding.stepFiveDisabledAccessibility.setOnCheckedChangeListener {
                _, isChecked ->
            if (isChecked) createViewModel.updateDisabledAccessibility(true)
            else createViewModel.updateDisabledAccessibility(false)
        }

        return binding.root
    }

}