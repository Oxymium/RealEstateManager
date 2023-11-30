package com.oxymium.realestatemanager.features.create.step_misc

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
import com.oxymium.realestatemanager.databinding.FragmentStepMiscBinding
import com.oxymium.realestatemanager.features.create.CreateViewModel
import com.oxymium.realestatemanager.model.EstateField
import com.oxymium.realestatemanager.viewmodel.factories.CreateViewModelFactory

class StepMiscFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var stepMiscBinding: FragmentStepMiscBinding
    private val binding get() = stepMiscBinding

    private val createViewModel: CreateViewModel by activityViewModels {
        CreateViewModelFactory(
            (activity?.application as EstatesApplication).agentRepository,
            (activity?.application as EstatesApplication).estateRepository,
            (activity?.application as EstatesApplication).pictureRepository
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

        stepMiscBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_step_misc, container, false)
        stepMiscBinding.lifecycleOwner = activity
        stepMiscBinding.createViewModel = createViewModel


        // --------------
        // TEXT WATCHERS
        // --------------

        // DESCRIPTION
        stepMiscBinding.stepMiscDescriptionInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) createViewModel.updateEstateField(EstateField.Description(s.toString()))
                    else createViewModel.updateEstateField(EstateField.Description(null))
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        // HIGH-SPEED INTERNET
        stepMiscBinding.stepMiscInternet.setOnCheckedChangeListener {
                _, isChecked ->
            if (isChecked) createViewModel.updateEstateField(EstateField.HighSpeedInternet(true))
            else createViewModel.updateEstateField(EstateField.HighSpeedInternet(false))
        }

        // FURNISHED
        stepMiscBinding.stepMiscFurnished.setOnCheckedChangeListener {
                _, isChecked ->
            if (isChecked) createViewModel.updateEstateField(EstateField.Furnished(true))
            else createViewModel.updateEstateField(EstateField.Furnished(false))
        }

        // GARDEN
        stepMiscBinding.stepMiscGarden.setOnCheckedChangeListener {
                _, isChecked ->
            if (isChecked) createViewModel.updateEstateField(EstateField.Garden(true))
            else createViewModel.updateEstateField(EstateField.Garden(false))
        }

        // DISABLED ACCESSIBILITY
        stepMiscBinding.stepMiscDisabledAccessibility.setOnCheckedChangeListener {
                _, isChecked ->
            if (isChecked) createViewModel.updateEstateField(EstateField.DisabledAccessibility(true))
            else createViewModel.updateEstateField(EstateField.DisabledAccessibility(false))
        }

        return binding.root
    }

}