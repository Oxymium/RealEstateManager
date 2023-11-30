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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.sqlite.db.SimpleSQLiteQuery
import com.oxymium.realestatemanager.ENERGY_RATINGS
import com.oxymium.realestatemanager.ESTATE_TYPES
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.database.EstatesApplication
import com.oxymium.realestatemanager.databinding.FragmentEstatesBinding
import com.oxymium.realestatemanager.model.Search
import com.oxymium.realestatemanager.utils.CustomSpinnerAdapter
import com.oxymium.realestatemanager.utils.DateUtils
import com.oxymium.realestatemanager.utils.OnSwipeTouchListener
import com.oxymium.realestatemanager.viewmodel.EstateViewModel
import com.oxymium.realestatemanager.viewmodel.factories.EstateViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
            (activity?.application as EstatesApplication).agentRepository,
            (activity?.application as EstatesApplication).estateRepository,
            (activity?.application as EstatesApplication).pictureRepository
        )
    }

    // RecyclerView Adapter
    private lateinit var estateAdapter: EstateAdapter

    // GridLayout Manager
    private lateinit var gridLayoutManager: GridLayoutManager

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

        fragmentEstatesBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_estates, container, false)
        fragmentEstatesBinding.lifecycleOwner = activity
        fragmentEstatesBinding.estateViewModel = estateViewModel
        fragmentEstatesBinding.include1.estateViewModel = estateViewModel

        // RecyclerView setup
        estateViewModel.isTablet.observe(viewLifecycleOwner) {
            gridLayoutManager = when (it) {
                // Single row for Tablet
                true -> GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
                // 3 items otherwise
                false -> GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            }
            fragmentEstatesBinding.fragmentEstatesRecyclerView.layoutManager = gridLayoutManager
        }

        // Refresh database
        estateViewModel.databaseRefreshed.observe(viewLifecycleOwner) {
            it?.let {
                //estateViewModel.getEstatesWithQuery(SimpleSQLiteQuery( "SELECT * FROM estate"))
                // Query field "cleaned"
                fragmentEstatesBinding.fragmentEstatesQuickSearchInputEdit.text = null
            }
        }

        // Observe flow of Estates, pass list to adapter
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                estateViewModel.estates.collectLatest {
                    estateAdapter.submitList(it)
                }
            }
        }

        // Initial value, collect all Estates
        estateViewModel.getEstatesWithQuery(SimpleSQLiteQuery( "SELECT * FROM estate"))

        // SEARCH QUERY
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                estateViewModel.searchQueryTest.collectLatest {
                    println(">>>>>>> QUICK SEARCH CHANGED")
                    println(">>>>>> $it")
                    estateViewModel.getEstatesWithQuery(SimpleSQLiteQuery(Search().generateFullSearchQuery(it)))
                }
            }
        }

        // SEARCH QUERY
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                estateViewModel.searchQuery.collectLatest {
                    println(">>>>>> SEARCH CHANGED")
                    println(">>>>> $it")
                    estateViewModel.getEstatesWithQuery(SimpleSQLiteQuery(it.generateFullSearchQuery(null)))
                }
            }
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
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // OnChange -> update Query
                s?.let {
                    estateViewModel.updateSearchQueryTest("$s")
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

            override fun onSwipeRight() {}
        })

        // -----------
        // FULL QUERY
        // -----------

        // SEARCH: TYPE
        val typeAdapter = CustomSpinnerAdapter(
            requireActivity(),
            R.layout.spinner_search,
            // Add ANY as a selector, then map the Labels to Strings
            listOf("Any") + ESTATE_TYPES.map { it.label }
        )

        fragmentEstatesBinding.include1.layoutSearchSpinnerType.adapter = typeAdapter
        fragmentEstatesBinding.include1.layoutSearchSpinnerType.onItemSelectedListener =
            object :
                AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (parent?.getItemAtPosition(position) == "Any") estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(type = null))
                    else estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(type = "${parent?.getItemAtPosition(position)}"))
                }
            }

        // SEARCH: ENERGY RATING
        val energyAdapter = CustomSpinnerAdapter(
            requireActivity(),
            R.layout.spinner_search,
            listOf("Any") + ENERGY_RATINGS.map { it })

        fragmentEstatesBinding.include1.layoutSearchSpinnerEnergy.adapter = energyAdapter
        fragmentEstatesBinding.include1.layoutSearchSpinnerEnergy.onItemSelectedListener =
            object :
                AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (parent?.getItemAtPosition(position)) {
                        "Any" -> estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(energyRating = null))
                        else -> estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(energyRating = "${parent?.getItemAtPosition(position)}"))
                    }
                }
            }

        // SEARCH: INTERNET
        val internetAdapter = CustomSpinnerAdapter(
            requireActivity(),
            R.layout.spinner_search,
            resources.getStringArray(R.array.array_search_choices).toList()
        )

        fragmentEstatesBinding.include1.layoutSearchSpinnerInternet.adapter = internetAdapter
        fragmentEstatesBinding.include1.layoutSearchSpinnerInternet.onItemSelectedListener =
            object :
                AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (parent?.getItemAtPosition(position)) {
                        "Both" -> estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(highSpeedInternet = null))
                        "Yes" -> estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(highSpeedInternet = "1"))
                        "No" -> estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(highSpeedInternet = "0"))
                    }
                }
            }

        // SEARCH: FURNISHED
        val furnishedAdapter = CustomSpinnerAdapter(
            requireActivity(),
            R.layout.spinner_search,
            resources.getStringArray(R.array.array_search_choices).toList()
        )

        fragmentEstatesBinding.include1.layoutSearchSpinnerFurnished.adapter = furnishedAdapter
        fragmentEstatesBinding.include1.layoutSearchSpinnerFurnished.onItemSelectedListener =
            object :
                AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (parent?.getItemAtPosition(position)) {
                        "Both" -> estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(furnished = null))
                        "Yes" -> estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(furnished = "1"))
                        "No" -> estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(furnished = "0"))
                    }
                }
            }

        // SEARCH: DISABILITY
        val disabilityAdapter = CustomSpinnerAdapter(
            requireActivity(),
            R.layout.spinner_search,
            resources.getStringArray(R.array.array_search_choices).toList()
        )

        fragmentEstatesBinding.include1.layoutSearchSpinnerDisabledAccessibility.adapter =
            disabilityAdapter
        fragmentEstatesBinding.include1.layoutSearchSpinnerDisabledAccessibility.onItemSelectedListener =
            object :
                AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (parent?.getItemAtPosition(position)) {
                        "Both" -> estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(disabledAccessibility = null))
                        "Yes" -> estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(disabledAccessibility = "1"))
                        "No" -> estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(disabledAccessibility = "0"))
                    }
                }
            }

        // SEARCH: GARDEN
        val gardenAdapter = CustomSpinnerAdapter(
            requireActivity(),
            R.layout.spinner_search,
            resources.getStringArray(R.array.array_search_choices).toList()
        )

        fragmentEstatesBinding.include1.layoutSearchSpinnerGarden.adapter = gardenAdapter
        fragmentEstatesBinding.include1.layoutSearchSpinnerGarden.onItemSelectedListener =
            object :
                AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (parent?.getItemAtPosition(position)) {
                        "Both" -> estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(garden = null))
                        "Yes" -> estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(garden = "1"))
                        "No" -> estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(garden = "0"))
                    }
                }
            }

        // SEARCH: SOLD
        val soldAdapter = CustomSpinnerAdapter(
            requireActivity(),
            R.layout.spinner_search,
            resources.getStringArray(R.array.array_search_choices).toList()
        )

        fragmentEstatesBinding.include1.layoutSearchSpinnerSold.adapter = soldAdapter
        fragmentEstatesBinding.include1.layoutSearchSpinnerSold.onItemSelectedListener =
            object :
                AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (parent?.getItemAtPosition(position)) {
                        "Both" -> {
                            estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(available = null))
                            // TOGGLE DATE SOLD VISIBILITY OFF
                            estateViewModel.updateSoldDateVisibility(false)
                            estateViewModel.resetDateValues(2)
                        }

                        "Yes" -> {
                            estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(available = "1"))
                            // TOGGLE DATE SOLD VISIBILITY ON
                            estateViewModel.updateSoldDateVisibility(true)
                        }

                        "No" -> {
                            estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(available = "0"))
                            // TOGGLE DATE SOLD VISIBILITY OFF
                            estateViewModel.updateSoldDateVisibility(false)
                            estateViewModel.resetDateValues(2)
                        }
                    }
                }
            }

        // SEARCH: MIN PRICE
        fragmentEstatesBinding.include1.layoutSearchPriceMinInput.addTextChangedListener(object :
            TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.isEmpty()) estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(minPrice = null))
                s.let { estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(minPrice = "$s")) }
            }

            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // SEARCH: MAX PRICE
        fragmentEstatesBinding.include1.layoutSearchPriceMaxInput.addTextChangedListener(object :
            TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.isEmpty()) estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(maxPrice = null))
                s.let { estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(maxPrice = "$s")) }
            }

            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // SEARCH: MIN SURFACE
        fragmentEstatesBinding.include1.layoutSearchSurfaceMinInput.addTextChangedListener(
            object :
                TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isEmpty()) estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(minSurface = null))
                    s.let { estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(minSurface = "$s")) }
                }

                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                }
            })

        // SEARCH: MAX SURFACE
        fragmentEstatesBinding.include1.layoutSearchSurfaceMaxInput.addTextChangedListener(
            object :
                TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isEmpty()) estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(maxSurface = null))
                    s.let { estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(maxSurface = "$s")) }
                }

                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                }
            })

        // SEARCH: MIN ROOMS
        fragmentEstatesBinding.include1.layoutSearchMiniRoomsAmountInput.addTextChangedListener(
            object :
                TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isEmpty()) estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(minRooms = null))
                    s.let { estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(minRooms = "$s")) }
                }

                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                }
            })

        // SEARCH: MIN BEDROOMS
        fragmentEstatesBinding.include1.layoutSearchMiniBedroomsAmountInput.addTextChangedListener(
            object :
                TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isEmpty()) estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(minBedrooms = null))
                    s.let { estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(minBedrooms = "$s")) }
                }

                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                }
            })

        // SEARCH: MIN BATHROOMS
        fragmentEstatesBinding.include1.layoutSearchMiniBathroomsAmountInput.addTextChangedListener(
            object :
                TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isEmpty()) estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(minBathrooms = null))
                    s.let { estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(minBathrooms = "$s")) }
                }

                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                }
            })

        // SEARCH: LOCATION
        fragmentEstatesBinding.include1.layoutSearchLocationInput.addTextChangedListener(
            object :
                TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isEmpty()) estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(location = null))
                    s.let { estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(location = "$s")) }
                }

                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                }
            })

        // SEARCH: NEARBY PLACES
        fragmentEstatesBinding.include1.layoutSearchNearbyPlacesInput.addTextChangedListener(
            object :
                TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isEmpty()) estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(nearby = null))
                    s.let { estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(nearby = "$s")) }
                }

                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                }
            })

        // SEARCH: MIN PICTURES
        fragmentEstatesBinding.include1.layoutSearchMiniPicturesAmountInput.addTextChangedListener(
            object :
                TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isEmpty()) estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(minPictures = null))
                    s.let { estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(minPictures = "$s")) }
                }

                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                }
            })

        // DATE SELECTOR: DISPLAY PROMPT
        estateViewModel.dateType.observe(viewLifecycleOwner) {
            it?.let { showAlertDialogDate(it) }
        }

        // ADDED DATE: START
        estateViewModel.startAddedDate.observe(viewLifecycleOwner) {
            it?.let {estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(startAddedDate = "$it")) }
        }

        // ADDED DATE: END
        estateViewModel.endAddedDate.observe(viewLifecycleOwner) {
            it?.let {estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(endAddedDate = "$it")) }

        }

        // SOLD DATE: START
        estateViewModel.startSoldDate.observe(viewLifecycleOwner) {
            it?.let {estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(startSoldDate = "$it")) }
        }

        // SOLD DATE: END
        estateViewModel.endSoldDate.observe(viewLifecycleOwner) {
            it?.let {estateViewModel.updateSearchQuery(estateViewModel.searchQuery.value.copy(endSoldDate = "$it")) }

        }

        // Return view
        return binding.root

    }

    private fun showAlertDialogDate(from: Int) {
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
        // Set max date to prevent setting sold date > today
        picker.maxDate = calendar.timeInMillis
        // Init with today's date & attach listener
        picker.init(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ) { _, year, month, day ->
            with(pickedDateCalendar) {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, day)
            }
            pickedDateInMillis = pickedDateCalendar.timeInMillis

            when (from) {
                1 -> estateViewModel.updateSearchQuery(
                    estateViewModel.searchQuery.value.copy(
                        startAddedDate = "$pickedDateInMillis"
                    )
                )

                2 -> estateViewModel.updateSearchQuery(
                    estateViewModel.searchQuery.value.copy(
                        endAddedDate = "$pickedDateInMillis"
                    )
                )

                3 -> estateViewModel.updateSearchQuery(
                    estateViewModel.searchQuery.value.copy(
                        startSoldDate = "$pickedDateInMillis"
                    )
                )

                4 -> estateViewModel.updateSearchQuery(
                    estateViewModel.searchQuery.value.copy(
                        endSoldDate = "$pickedDateInMillis"
                    )
                )
            }
        }

        val title: String = if (from == 1) "Starting date" else "Ending date"

        with(builder) {
            setTitle(title)
            setView(picker)
            setPositiveButton("Save") { _, _ ->
                // Update viewModel with string date in format DD/MM/YYYY with DD < 10 = 0X && MM < 10 = 0X
                when (from) {
                    1 -> pickedDateInMillis?.let { estateViewModel.updateStartAddedDate(it) }
                    2 -> pickedDateInMillis?.let { estateViewModel.updateEndAddedDate(it) }
                    3 -> pickedDateInMillis?.let { estateViewModel.updateStartSoldDate(it) }
                    4 -> pickedDateInMillis?.let { estateViewModel.updateEndSoldDate(it) }
                }
            }
            setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            show()
        }
    }
}





