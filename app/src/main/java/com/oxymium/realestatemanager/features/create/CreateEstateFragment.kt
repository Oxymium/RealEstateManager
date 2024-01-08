package com.oxymium.realestatemanager.features.create

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.oxymium.realestatemanager.CREATE_MENU_STEPS
import com.oxymium.realestatemanager.R
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
import com.oxymium.realestatemanager.features.create.steps.MenuStepListener
import com.oxymium.realestatemanager.features.create.steps.MenuStepsAdapter
import com.oxymium.realestatemanager.features.create.steps.RecyclerViewScrollListener
import com.oxymium.realestatemanager.model.ReachedSide
import com.oxymium.realestatemanager.model.mock.generateOneRandomEstate
import com.oxymium.realestatemanager.model.toNearbyPlaceList
import com.oxymium.realestatemanager.viewmodel.CreateViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

// ---------------
// StepOneFragment
// ---------------

class CreateEstateFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentCreateEstateBinding: FragmentCreateEstateBinding
    private val binding get() = fragmentCreateEstateBinding

    // RecyclerView
    private lateinit var menuStepsAdapter: MenuStepsAdapter

    private val createViewModel: CreateViewModel by activityViewModel()

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

        observeEditedEstate()
        observeSaveOrUpdateEstate()
        observeCreationStep()
        observeMissingElements()

        // RecyclerView setup
        val linearLayoutManager = LinearLayoutManager(requireActivity(), GridLayoutManager.HORIZONTAL, false)
        fragmentCreateEstateBinding.navigatorBar.navigatorCreateRecyclerView.layoutManager = linearLayoutManager

        // Observe list of steps
        createViewModel.createMenuSteps.observe(viewLifecycleOwner) { createMenuSteps ->
            menuStepsAdapter.submitList(createMenuSteps)
        }

        // Setup adapter
        menuStepsAdapter = MenuStepsAdapter(
            // StepListener
            MenuStepListener {
                createViewModel.updateCreateMenuStep(it)
            }
        )

        // RecyclerView adapter init
        fragmentCreateEstateBinding.navigatorBar.navigatorCreateRecyclerView.adapter = menuStepsAdapter

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
        createViewModel.estate.observe(viewLifecycleOwner) {
            if (it?.id != null) {
                // Load existing nearby places
                it.nearbyPlaces?.toNearbyPlaceList()?.let { nearbyPlaces -> createViewModel.updateNearbyPlaces(nearbyPlaces) }
                // Load existing secondary pictures
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
    private fun observeCreationStep() {
        lifecycleScope.launch {
            createViewModel.createMenuStep.collect { createMenuStep ->
                this@CreateEstateFragment.replaceFragment(
                    when (createMenuStep.id) {
                        CREATE_MENU_STEPS[0].id -> StepOverviewFragment()
                        CREATE_MENU_STEPS[1].id -> StepAgentFragment()
                        CREATE_MENU_STEPS[2].id -> StepTypeFragment()
                        CREATE_MENU_STEPS[3].id -> StepValuesEnergyScoreFragment()
                        CREATE_MENU_STEPS[4].id -> StepMainPictureFragment()
                        CREATE_MENU_STEPS[5].id -> StepSecondaryPicturesFragment()
                        CREATE_MENU_STEPS[6].id -> StepMiscFragment()
                        CREATE_MENU_STEPS[7].id -> StepAddressFragment()
                        CREATE_MENU_STEPS[8].id -> StepNearbyPlacesFragment()
                        else -> StepOverviewFragment()
                    }
                )
                // Update the currentMenuStep value to display the step in the Navigator
                createViewModel.updateCurrentCreateMenuStep(createMenuStep)
                // Update the list to flip the value of the selected item
                createViewModel.updateCreateMenuSteps(
                    createViewModel.createMenuSteps.value?.map {
                        it.copy(isSelected = it.id == createMenuStep.id)
                    }
                )
            }
        }
    }

    // If elements are missing, display prompt with said missing elements
    private fun observeMissingElements() {
        viewLifecycleOwner.lifecycleScope.launch {
            createViewModel.displayMissingElementsErrorDialog.collect { displayMissingErrorDialog ->
                if (displayMissingErrorDialog) displayMissingDialog()
            }
        }
    }

    // Final alert to save
    private fun observeSaveOrUpdateEstate()  {
        viewLifecycleOwner.lifecycleScope.launch {
            createViewModel.saveOrUpdateEstate.collect { saveOrUpdateEstate ->
                if (saveOrUpdateEstate) displaySaveDialog()
            }
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
                val randomEstate = generateOneRandomEstate()
                createViewModel.fillEstateFields(randomEstate)
                randomEstate.nearbyPlaces?.toNearbyPlaceList()?.let { createViewModel.updateNearbyPlaces(it) }
                createViewModel.fillSecondaryPictures()
            }
        }
        val dialog: AlertDialog? = builder?.create()
        dialog?.show()
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
                createViewModel.insertEstateAndPicturesIntoDatabase()
            }
            this?.setNegativeButton(R.string.alert_negative) { _, _ ->
            }
        }
        val dialog: AlertDialog? = builder?.create()
        dialog?.show()
    }

}