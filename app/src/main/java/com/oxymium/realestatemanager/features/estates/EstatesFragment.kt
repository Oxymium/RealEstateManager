package com.oxymium.realestatemanager.features.estates

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.DatePicker
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.oxymium.realestatemanager.ESTATE_TYPES
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.database.EstatesApplication
import com.oxymium.realestatemanager.databinding.FragmentEstatesBinding
import com.oxymium.realestatemanager.utils.CustomSpinnerAdapter
import com.oxymium.realestatemanager.utils.DateUtils
import com.oxymium.realestatemanager.utils.EstateListener
import com.oxymium.realestatemanager.utils.OnSwipeTouchListener
import com.oxymium.realestatemanager.viewmodel.EstateViewModel
import com.oxymium.realestatemanager.viewmodel.EstateViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.Calendar


// ---------------
// EstatesFragment
// ---------------

class EstatesFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentEstatesBinding: FragmentEstatesBinding
    private val binding get() = fragmentEstatesBinding

    // EstateViewModel
    private val estateViewModel: EstateViewModel by activityViewModels {
        EstateViewModelFactory(
            (activity?.application as EstatesApplication).repository3,
            (activity?.application as EstatesApplication).repository,
            (activity?.application as EstatesApplication).repository2
        )
    }

    // RecyclerView Adapter
    private lateinit var estateAdapter: EstateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(fragmentTAG, "onCreate: ")

    }

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentEstatesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_estates, container, false)

        fragmentEstatesBinding.lifecycleOwner = activity
        fragmentEstatesBinding.estateViewModel = estateViewModel
        fragmentEstatesBinding.include1.estateViewModel = estateViewModel

        // RecyclerView setup
        var gridLayoutManager: GridLayoutManager
        estateViewModel.isTablet.observe(viewLifecycleOwner) {
            gridLayoutManager = when (it) {
                // Single row for Tablet
                true -> GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
                // 3 items otherwise
                false -> GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            }
            fragmentEstatesBinding.fragmentEstatesRecyclerView.layoutManager = gridLayoutManager
        }

        // Observe estate list
        estateViewModel.estateListLiveData.observe(viewLifecycleOwner){
            estateAdapter.submitList(it)
            // Update listSize value to display number of Estate in the list
            estateViewModel.estatesListSize.value = it.size
        }

        // Initialize query
        estateViewModel.queryValue.observe(viewLifecycleOwner) {
            estateViewModel.setSearchQuery(it, 1)
        }

        // Observe search query & update
        estateViewModel.searchQuery.observe(viewLifecycleOwner) {
                searchQuery ->
            estateViewModel.setSearchQuery(searchQuery.generateFullSearchQuery(), 2)
            println(searchQuery.generateFullSearchQuery() + " THIS HAS CHANGED")
        }

        // Reset query value when user swipe screen
        fragmentEstatesBinding.fragmentEstateSwipeRefreshLayout.setOnRefreshListener {
            estateViewModel.setSearchQuery("", 1)
            // Query field "cleaned"
            fragmentEstatesBinding.fragmentEstatesQuickSearchInputEdit.text = null
            // Stop refresh animation
            fragmentEstatesBinding.fragmentEstateSwipeRefreshLayout.isRefreshing = false
        }

        // Setup adapter
        estateAdapter = EstateAdapter(EstateListener {
            // Send selected Estate to ViewModel
            // ID
            estateViewModel.updateSelectedEstateId(it.id)
            // AGENT ID
            estateViewModel.updateAgentId(it.agent_id)

            estateViewModel.toggleShouldStartDetailsFragment(true)
        })

        // Adapter init
        fragmentEstatesBinding.fragmentEstatesRecyclerView.adapter = estateAdapter

        // (Quick Search) Text listener
        fragmentEstatesBinding.fragmentEstatesQuickSearchInputEdit.addTextChangedListener(object :
            TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // OnChange -> update Query
                s?.let {
                    estateViewModel.setSearchQuery(it.toString(), 1)

                }
            }
        })

        // -----------------
        // SWIPE INTERACTION
        // -----------------
        val viewTest = fragmentEstatesBinding.include1.root
        viewTest.setOnTouchListener(object : OnSwipeTouchListener(requireActivity()) {
            override fun onSwipeLeft() {
                estateViewModel.updateToggleSearchButton(false)
            }

            override fun onSwipeRight() {
                println("RIGHT")
            }
        })

        // -----------
        // FULL QUERY
        // -----------

        // SEARCH: TYPE
        val typeAdapter = CustomSpinnerAdapter(
            requireActivity(),
            R.layout.spinner_type,
            // Add ANY as a selector, then map the Labels to Strings
            listOf("Any") + ESTATE_TYPES.map { it.label }
        )

        fragmentEstatesBinding.include1.layoutSearchTypeSpinner.adapter = typeAdapter
        fragmentEstatesBinding.include1.layoutSearchTypeSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long){
                if (parent?.getItemAtPosition(position) == "Any") { estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.type = null} }
                else{ estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.type = parent?.getItemAtPosition(position).toString() }
                }
            }
        }

        // SEARCH: ENERGY TYPE
        val energyAdapter = CustomSpinnerAdapter(
            requireActivity(),
            R.layout.spinner_energy,
            resources.getStringArray(R.array.array_search_energy_score).toList()
        )

        fragmentEstatesBinding.include1.layoutSearchEnergyScoreSpinner.adapter = energyAdapter
        fragmentEstatesBinding.include1.layoutSearchEnergyScoreSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long){
                when (parent?.getItemAtPosition(position)){
                    "Any" -> estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.energy = null }
                    else -> estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.energy = parent?.getItemAtPosition(position).toString() }
                }
            }
        }

        // SEARCH: TRIPLE CHOICE
        val internetAdapter = CustomSpinnerAdapter(
            requireActivity(),
            R.layout.spinner_internet,
            resources.getStringArray(R.array.array_search_choices).toList()
        )

        // SEARCH: INTERNET
        fragmentEstatesBinding.include1.layoutSearchInternetSpinner.adapter = internetAdapter
        fragmentEstatesBinding.include1.layoutSearchInternetSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long){
                when (parent?.getItemAtPosition(position)){
                    "Both" -> estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.highSpeedInternet = null }
                    "Yes" -> estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.highSpeedInternet = "1"}
                    "No" -> estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.highSpeedInternet = "0"}
                }
            }
        }

        // SEARCH: FURNISHED
        val furnishedAdapter = CustomSpinnerAdapter(
            requireActivity(),
            R.layout.spinner_furnished,
            resources.getStringArray(R.array.array_search_choices).toList()
        )

        // SEARCH: FURNISHED
        fragmentEstatesBinding.include1.layoutSearchFurnishedSpinner.adapter = furnishedAdapter
        fragmentEstatesBinding.include1.layoutSearchFurnishedSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long){
                when (parent?.getItemAtPosition(position)){
                    "Both" -> estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.furnished = null }
                    "Yes" -> estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.furnished = "1"}
                    "No" -> estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.furnished = "0"}
                }
            }
        }

        // SEARCH: DISABILITY
        val disabilityAdapter = CustomSpinnerAdapter(
            requireActivity(),
            R.layout.spinner_disability,
            resources.getStringArray(R.array.array_search_choices).toList()
        )

        fragmentEstatesBinding.include1.layoutSearchDisabledAccessibilitySpinner.adapter = disabilityAdapter
        fragmentEstatesBinding.include1.layoutSearchDisabledAccessibilitySpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long){
                when (parent?.getItemAtPosition(position)){
                    "Both" -> estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.disabledAccessibility = null }
                    "Yes" -> estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.disabledAccessibility = "1"}
                    "No" -> estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.disabledAccessibility = "0"}
                }
            }
        }

        // SEARCH: GARDEN
        val gardenAdapter = CustomSpinnerAdapter(
            requireActivity(),
            R.layout.spinner_garden,
            resources.getStringArray(R.array.array_search_choices).toList()
        )

        fragmentEstatesBinding.include1.layoutSearchGardenSpinner.adapter = gardenAdapter
        fragmentEstatesBinding.include1.layoutSearchGardenSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long){
                when (parent?.getItemAtPosition(position)){
                    "Both" -> estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.garden = null }
                    "Yes" -> estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.garden = "1"}
                    "No" -> estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.garden= "0"}
                }
            }
        }

        // SEARCH: MIN PRICE
        fragmentEstatesBinding.include1.layoutSearchPriceMinInput.addTextChangedListener(object :
            TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.isEmpty()) { estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.minPrice = null}}
                s.let { estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.minPrice = s.toString()}}}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {} })

        // SEARCH: MAX PRICE
        fragmentEstatesBinding.include1.layoutSearchPriceMaxInput.addTextChangedListener(object :
            TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.isEmpty()) { estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.maxPrice = null} }
                s.let { estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.maxPrice = s.toString()}} }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {} })

        // SEARCH: MIN SURFACE
        fragmentEstatesBinding.include1.layoutSearchSurfaceMinInput.addTextChangedListener(object :
            TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.isEmpty()) { estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.minSurface = null} }
                s.let { estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.minSurface = s.toString()}}}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {} })

        // SEARCH: MAX SURFACE
        fragmentEstatesBinding.include1.layoutSearchSurfaceMaxInput.addTextChangedListener(object :
            TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.isEmpty()) { estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.maxSurface = null}}
                s.let {estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.maxSurface = s.toString()}} }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {} })

        // SEARCH: MIN ROOMS
        fragmentEstatesBinding.include1.layoutSearchMiniRoomsAmountInput.addTextChangedListener(
            object :
                TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isEmpty()) { estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.minRooms = null}}
                    s.let { estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.minRooms = s.toString()} } }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int){}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {} })

        // SEARCH: MIN BEDROOMS
        fragmentEstatesBinding.include1.layoutSearchMiniBedroomsAmountInput.addTextChangedListener(
            object :
                TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isEmpty()) { estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.minBedrooms = null}}
                    s.let { estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.minBedrooms = s.toString()} } }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int){}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {} })

        // SEARCH: MIN BATHROOMS
        fragmentEstatesBinding.include1.layoutSearchMiniBathroomsAmountInput.addTextChangedListener(
            object :
                TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isEmpty()) { estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.minBathrooms = null}}
                    s.let { estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.minBathrooms = s.toString()} } }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int){}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {} })

        // SEARCH: LOCATION
        fragmentEstatesBinding.include1.layoutSearchLocationInput.addTextChangedListener(
            object :
                TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isEmpty()) { estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.location = null}}
                    s.let { estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.location = s.toString()} } }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int){}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {} })

        // SEARCH: NEARBY PLACES
        fragmentEstatesBinding.include1.layoutSearchNearbyInput.addTextChangedListener(
            object :
                TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isEmpty()) { estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.nearby = null}}
                    s.let { estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.nearby = s.toString()} } }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int){}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {} })

        // SEARCH: MIN PICTURES
        fragmentEstatesBinding.include1.layoutSearchMiniPicturesAmountInput.addTextChangedListener(
            object :
                TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isEmpty()) { estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.minPictures = null}}
                    s.let { estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.minPictures = s.toString()} } }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int){}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {} })

        // DATE SELECTOR: DISPLAY PROMPT
        estateViewModel.wasDateClicked.observe(viewLifecycleOwner) { dateClicked ->
            when (dateClicked) {
                1 -> {
                    alertDialogDate(dateClicked)
                    // Uncheck "refresher" checkBox
                    fragmentEstatesBinding.include1.layoutSearchAllTimePeriods.isChecked = false
                    estateViewModel.wasDateClicked.value = 0
                }
                2 -> {
                    alertDialogDate(dateClicked)
                    fragmentEstatesBinding.include1.layoutSearchAllTimePeriods.isChecked = false
                    estateViewModel.wasDateClicked.value = 0
                }
                3 -> {
                    alertDialogDate(dateClicked)
                    // todo FIX
                    //fragmentEstatesBinding.include1.layoutSearchAllTimeSold.isChecked = false
                    estateViewModel.wasDateClicked.value = 0
                }
                4 -> {
                    alertDialogDate(dateClicked)
                    // todo FIX
                    //fragmentEstatesBinding.include1.layoutSearchAllTimeSold.isChecked = false
                    estateViewModel.wasDateClicked.value = 0
                }
            }
        }

        // SEARCH: SELL
        val sellAdapter = CustomSpinnerAdapter(
            requireActivity(),
            R.layout.spinner_sell,
            resources.getStringArray(R.array.array_search_choices).toList()
        )

        // SEARCH: FURNISHED
        fragmentEstatesBinding.include1.layoutSearchSellSpinner.adapter = sellAdapter
        fragmentEstatesBinding.include1.layoutSearchSellSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long){
                when (parent?.getItemAtPosition(position)){
                    "Both" -> estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.available = null }
                    "Yes" -> estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.available = "1"}
                    "No" -> estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.available = "0"}
                }
            }
        }

        /*
        // CHECKBOXES reset time periods
        fragmentEstatesBinding.include1.layoutSearchAllTimePeriods.setOnCheckedChangeListener{
                _, isChecked ->
            if (isChecked) {
                println("Called checbox clicked")
                estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.startingDate = null }
                estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.endingDate = null }
                // TODO updateViewModel with new values
                //estateViewModel.startingAddedDate.value = estateViewModel.startingAddedDate.value?.run{ 0L}
                //estateViewModel.endingAddedDate.value = estateViewModel.endingAddedDate.value?.run{ 0L}
            }}

        fragmentEstatesBinding.include1.layoutSearchAllTimeSold.setOnCheckedChangeListener{
                _, isChecked ->
            if (isChecked) {
                estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.startingDateSold = null }
                estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.endingDateSold = null }
                // TODO updateViewModel with new values
                //estateViewModel.startingSoldDate.value = estateViewModel.startingSoldDate.value?.apply{ estateViewModel.startingSoldDate.value = 0L}
                //estateViewModel.endingSoldDate.value = estateViewModel.endingSoldDate.value?.apply{ estateViewModel.endingSoldDate.value = 0L}
            }}

        */

        // Return view
        return binding.root

    }

    fun alertDialogDate(from: Int) {
        // Get current date
        val calendar: Calendar = Calendar.getInstance()
        // Day at 0.00.00
        calendar.timeInMillis = DateUtils().getTodayInMillis()


        val pickedDateCalendar = Calendar.getInstance()
        pickedDateCalendar.timeInMillis = DateUtils().getTodayInMillis()

        var pickedDateInMillis: Long? = null

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

            pickedDateCalendar.set(Calendar.YEAR, year)
            pickedDateCalendar.set(Calendar.MONTH, month)
            pickedDateCalendar.set(Calendar.DAY_OF_MONTH, day)
            pickedDateInMillis = pickedDateCalendar.timeInMillis

            if (from == 1){ estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.startingDate = pickedDateInMillis.toString()}}
            if (from == 2){ estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.endingDate = pickedDateInMillis.toString()}}
            if (from == 3){ estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.startingDateSold = pickedDateInMillis.toString()}}
            if (from == 4){ estateViewModel.searchQuery.value = estateViewModel.searchQuery.value?.also { it.endingDateSold = pickedDateInMillis.toString()}}

            Log.d(fragmentTAG, "selected date: $day/$month/$year")
            Log.d(fragmentTAG, "long date: $pickedDateInMillis")
        }

        val title: String = if (from == 1){ "Starting date" }else{ "Ending date" }
        // TODO add more titles
        builder.setTitle(title)
        builder.setView(picker)
        builder.setPositiveButton("Save")
        { _, _ ->

            // Update viewModel with string date in format DD/MM/YYYY with DD < 10 = 0X && MM < 10 = 0X
            if (from ==1){ estateViewModel.startingAddedDate.value = pickedDateInMillis }
            if (from ==2){ estateViewModel.endingAddedDate.value = pickedDateInMillis }
            if (from ==3){ estateViewModel.startingSoldDate.value = pickedDateInMillis }
            if (from ==4){ estateViewModel.endingSoldDate.value = pickedDateInMillis }

        }

        builder.setNegativeButton(
            "Cancel"
        ) { dialog, _ -> dialog.cancel() }

        builder.show()
    }
}





