package com.oxymium.realestatemanager.features.create.step_values_energy_score

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.databinding.FragmentStepValuesEnergyScoreBinding
import com.oxymium.realestatemanager.model.EstateField
import com.oxymium.realestatemanager.model.getEnergyRating
import com.oxymium.realestatemanager.viewmodel.CreateViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class StepValuesEnergyScoreFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var stepValuesEnergyScoreBinding: FragmentStepValuesEnergyScoreBinding
    private val binding get() = stepValuesEnergyScoreBinding

    private val createViewModel: CreateViewModel by activityViewModel<CreateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(fragmentTAG, "onCreate: ")


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        stepValuesEnergyScoreBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_step_values_energy_score, container, false)
        stepValuesEnergyScoreBinding.lifecycleOwner = activity
        stepValuesEnergyScoreBinding.createViewModel = createViewModel

        // -------------
        // TEXT WATCHERS
        // -------------

        // PRICE
        stepValuesEnergyScoreBinding.stepTwoPriceInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) {
                        createViewModel.updateEstateField(EstateField.Price(s.toString().toInt()))
                    }
                    if (s.isEmpty()) createViewModel.updateEstateField(EstateField.Price(null))
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        // SURFACE
        stepValuesEnergyScoreBinding.stepTwoSurfaceInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) createViewModel.updateEstateField(EstateField.Surface(s.toString().toInt()))
                    else createViewModel.updateEstateField(EstateField.Surface(null))
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        // ROOMS
        stepValuesEnergyScoreBinding.stepTwoRoomsInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.toString().startsWith("0") && s.length > 1){
                        stepValuesEnergyScoreBinding.stepTwoRoomsInput.setText("0")
                    }
                    if (s.isNotEmpty()) createViewModel.updateEstateField(EstateField.Rooms(s.toString().toInt()))
                    else createViewModel.updateEstateField(EstateField.Rooms(null))
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        // BEDROOMS
        stepValuesEnergyScoreBinding.stepTwoBedroomsInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) createViewModel.updateEstateField(EstateField.Bedrooms(s.toString().toInt()))
                    else createViewModel.updateEstateField(EstateField.Bedrooms(null))
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        // BATHROOMS
        stepValuesEnergyScoreBinding.stepTwoBathroomsInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) createViewModel.updateEstateField(EstateField.Bathrooms(s.toString().toInt()))
                    else createViewModel.updateEstateField(EstateField.Bathrooms(null))
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        // ENERGY SCORE
        stepValuesEnergyScoreBinding.stepTwoEnergyScoreInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) {
                        createViewModel.updateEstateField(EstateField.EnergyScore(s.toString().toInt()))
                        createViewModel.updateEstateField(EstateField.EnergyRating(getEnergyRating(s.toString().toInt())))
                    }
                    else {
                        createViewModel.updateEstateField(EstateField.EnergyScore(null))
                        createViewModel.updateEstateField(EstateField.EnergyRating(null))
                    }
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        return binding.root
    }

}