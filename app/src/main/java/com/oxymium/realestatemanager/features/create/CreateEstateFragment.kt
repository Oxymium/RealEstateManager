package com.oxymium.realestatemanager.features.create

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.database.EstatesApplication
import com.oxymium.realestatemanager.databinding.FragmentCreateEstateBinding
import com.oxymium.realestatemanager.features.create.step_address.StepAddressFragment
import com.oxymium.realestatemanager.features.create.step_agent.StepAgentFragment
import com.oxymium.realestatemanager.features.create.step_misc.StepMiscFragment
import com.oxymium.realestatemanager.features.create.step_nearby_places.StepNearbyPlacesFragment
import com.oxymium.realestatemanager.features.create.step_overview.StepOverviewFragment
import com.oxymium.realestatemanager.features.create.step_picture_main.StepMainPictureFragment
import com.oxymium.realestatemanager.features.create.step_pictures_secondary.StepSecondaryPicturesFragment
import com.oxymium.realestatemanager.features.create.step_type.StepTypeFragment
import com.oxymium.realestatemanager.features.create.step_values_energy_score.StepValuesEnergyScoreFragment
import com.oxymium.realestatemanager.features.create.steps.RecyclerViewScrollListener
import com.oxymium.realestatemanager.features.create.steps.StepListener
import com.oxymium.realestatemanager.features.create.steps.StepsAdapter
import com.oxymium.realestatemanager.model.ReachedSide
import com.oxymium.realestatemanager.model.mock.generateOneRandomEstate
import com.oxymium.realestatemanager.viewmodel.factories.CreateViewModelFactory

// ---------------
// StepOneFragment
// ---------------

class CreateEstateFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentCreateEstateBinding: FragmentCreateEstateBinding
    private val binding get() = fragmentCreateEstateBinding

    // RecyclerView
    private lateinit var stepsAdapter: StepsAdapter

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

        fragmentCreateEstateBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_estate, container, false)
        fragmentCreateEstateBinding.lifecycleOwner = activity
        fragmentCreateEstateBinding.createViewModel = createViewModel
        fragmentCreateEstateBinding.navigatorHeader.createViewModel = createViewModel
        fragmentCreateEstateBinding.navigatorBar.createViewModel = createViewModel

        createViewModel.currentStep.value?.copy(number = 0)
            ?.let { createViewModel.updateCurrentStep(it) }

        observeEditedEstate()

        observeCreationSteps()
        observeMissingElements()

        // RecyclerView setup
        val linearLayoutManager = LinearLayoutManager(requireActivity(), GridLayoutManager.HORIZONTAL, false)
        fragmentCreateEstateBinding.navigatorBar.navigatorCreateRecyclerView.layoutManager = linearLayoutManager

        // Observe list of steps
        createViewModel.createSteps.observe(viewLifecycleOwner){
            stepsAdapter.submitList(it?.toList())
            println(it)
        }

        // Setup adapter
        stepsAdapter = StepsAdapter(
            // StepListener
            StepListener {
                createViewModel.updateCurrentStep(it)
            }
        )

        createViewModel.currentStep.observe(viewLifecycleOwner) { step ->
            step?.let {
                createViewModel.updateCreateStep(createViewModel.createSteps.value?.map { createSteps ->
                    createSteps.copy(isSelected = step.number == createSteps.number)
                })
            }

        }

        // RecyclerView adapter init
        fragmentCreateEstateBinding.navigatorBar.navigatorCreateRecyclerView.adapter = stepsAdapter

        // RecyclerView side reached
        val onScrollToBeginning: () -> Unit = {
            createViewModel.updateReachedStepSide(ReachedSide.LeftSide)
        }

        val onScrollToEnd: () -> Unit = {
            createViewModel.updateReachedStepSide(ReachedSide.RightSide)
        }

        val scrollListener = RecyclerViewScrollListener(onScrollToBeginning, onScrollToEnd, null, null)
        fragmentCreateEstateBinding.navigatorBar.navigatorCreateRecyclerView.addOnScrollListener(scrollListener)

        return binding.root
    }

    private fun observeEditedEstate(){
        createViewModel.editedEstate.observe(viewLifecycleOwner){
            // If there's an instance of an Estate to edit, pre-load all values into the fields
            it?.let {
                // LOAD ESTATE
                createViewModel.updateEstate(it)
                // LOAD SECONDARY PICTURES
                createViewModel.getPicturesForGivenEstateId(it.id)
            }
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
            if (it.number == selectedStep) return@observe
            this.replaceFragment(when (it.number) {
                null -> CreateEstatePlaceholderFragment()
                0 -> StepOverviewFragment()
                1 -> StepAgentFragment()
                2 -> StepTypeFragment()
                3 -> StepValuesEnergyScoreFragment()
                4 -> StepMainPictureFragment()
                5 -> StepSecondaryPicturesFragment()
                6 -> StepMiscFragment()
                7 -> StepAddressFragment()
                8 -> StepNearbyPlacesFragment()
                else -> CreateEstatePlaceholderFragment()
            })
            createViewModel.updateSelectedStep(it.number)
        }
    }

    // If elements are missing, display prompt with said missing elements, otherwise, display saving prompt
    private fun observeMissingElements(){
        createViewModel.missingElementsAsStrings.observe(viewLifecycleOwner){
            if (it != null) {
                displayMissingDialog()
                // Reset
                //createViewModel.missingElementsAsStrings.value = null
            }
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
            this?.setNegativeButton(R.string.alert_random_debug) { _, _ ->
                createViewModel.fillEstateFields(generateOneRandomEstate())
                createViewModel.fillSecondaryPictures() }
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
                    null -> createViewModel.insertEstateAndPicturesIntoDatabase()
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