package com.oxymium.realestatemanager.features.create.step_one

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.database.EstatesApplication
import com.oxymium.realestatemanager.databinding.FragmentCreateEstateBinding
import com.oxymium.realestatemanager.features.create.CreateViewModel
import com.oxymium.realestatemanager.features.create.step_five.StepFiveFragment
import com.oxymium.realestatemanager.features.create.step_four.StepFourFragment
import com.oxymium.realestatemanager.features.create.step_six.StepSixFragment
import com.oxymium.realestatemanager.features.create.step_three.StepThreeFragment
import com.oxymium.realestatemanager.features.create.step_two.StepTwoFragment
import com.oxymium.realestatemanager.features.estates.CreateEstatePlaceholderFragment
import com.oxymium.realestatemanager.generateRandomEstate
import com.oxymium.realestatemanager.viewmodel.CreateViewModelFactory

// ---------------
// StepOneFragment
// ---------------

class CreateEstateFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentCreateEstateBinding: FragmentCreateEstateBinding
    private val binding get() = fragmentCreateEstateBinding

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

        fragmentCreateEstateBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_estate, container, false)
        fragmentCreateEstateBinding.lifecycleOwner = activity
        fragmentCreateEstateBinding.createViewModel = createViewModel
        fragmentCreateEstateBinding.navigatorBar.createViewModel = createViewModel

        createViewModel.updateCurrentStep(0)

        observeEditedEstate()

        observeCreationSteps()
        observeMissingElements()

        return binding.root
    }

    private fun observeEditedEstate(){
        createViewModel.editedEstate.observe(viewLifecycleOwner){
            // If there's an instance of an Estate to edit, pre-load all values into the fields
            it?.let { createViewModel.preloadAllFieldsWithEstateToEditValues(it) }
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        fragment.tag
        transaction?.replace(R.id.fragment_view_container, fragment, "")
        transaction?.commit()
    }

    // Navigation handler for Creation process
    private fun observeCreationSteps(){
        val selectedStep = createViewModel.selectedStep.value
        createViewModel.currentStep.observe(viewLifecycleOwner) {
            if (it == selectedStep) return@observe
            this.replaceFragment(when (it) {
                null -> CreateEstatePlaceholderFragment()
                1 -> StepOneFragment()
                2 -> StepTwoFragment()
                3 -> StepThreeFragment()
                4 -> StepFourFragment()
                5 -> StepFiveFragment()
                6 -> StepSixFragment()
                else -> CreateEstatePlaceholderFragment()
            })
            createViewModel.updateSelectedStep(it)
        }
    }

    private fun observeMissingElements(){
        createViewModel.missingElementsAsStrings.observe(viewLifecycleOwner){
            if (it != null) displayMissingDialog()
            else displaySaveDialog()
        }
    }

    // Missing elements prompt
    private fun displayMissingDialog() {
        val builder: AlertDialog.Builder? = activity?.let { AlertDialog.Builder(it) }
        with(builder) {
            this?.setTitle(R.string.alert_missing_title)
            this?.setMessage(createViewModel.missingElementsAsStrings.value)
        }.apply {
            this?.setNeutralButton(R.string.alert_neutral) { _, _ -> }
            // Testing purposes
            this?.setNegativeButton(R.string.alert_random_debug) { _, _ -> createViewModel.fillCreateWithOneRandomEstate(generateRandomEstate()) }
        }
        val dialog: AlertDialog? = builder?.create()
        dialog!!.show()
    }

    // Save prompt
    private fun displaySaveDialog() {
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }
        with(builder) {
            this?.setTitle(R.string.alert_create_title)
            this?.setMessage(R.string.alert_create_message)
        }.apply {
            this?.setPositiveButton(R.string.alert_positive) { _, _ ->
                when(createViewModel.editedEstate.value){
                    // IF NO ESTATE TO EDIT = INSERT (CREATE NEW ONE)
                    null -> createViewModel.insertEstateIntoDatabase()
                    // OTHERWISE, EDIT EXISTING ESTATE
                    else -> createViewModel.updateEstateIntoDatabase()
                }

            }
            this?.setNegativeButton(R.string.alert_negative) { _, _ ->
            }
        }
        val dialog: AlertDialog? = builder?.create()
        dialog!!.show()
    }

}