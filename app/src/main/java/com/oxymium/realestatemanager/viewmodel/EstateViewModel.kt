package com.oxymium.realestatemanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.sqlite.db.SimpleSQLiteQuery
import com.oxymium.realestatemanager.database.agent.AgentRepository
import com.oxymium.realestatemanager.database.estate.EstateRepository
import com.oxymium.realestatemanager.database.picture.PictureRepository
import com.oxymium.realestatemanager.misc.RANDOM_PICTURES
import com.oxymium.realestatemanager.model.Search
import com.oxymium.realestatemanager.model.databaseitems.Agent
import com.oxymium.realestatemanager.model.databaseitems.Estate
import com.oxymium.realestatemanager.model.databaseitems.Picture
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


// ---------------
// EstateViewModel
// ---------------

class EstateViewModel(private val agentRepository: AgentRepository, private val estateRepository: EstateRepository, private val pictureRepository: PictureRepository): ViewModel() {

    // -----------
    // NAVIGATION
    // -----------
    // TABLET OR NOT
    val isTablet: LiveData<Boolean> get() = _isTablet
    private val _isTablet = MutableLiveData<Boolean>()
    fun toggleIsTablet(toggle: Boolean){
        _isTablet.value = toggle
    }

    // Navigation 1 = from Maps, 2 = from Estates
    val shouldStartDetailsFragment: MutableLiveData<Boolean?> get() = _shouldStartDetailsFragment
    private val _shouldStartDetailsFragment = MutableLiveData<Boolean?>(null)
    fun toggleShouldStartDetailsFragment(toggle: Boolean?){
        _shouldStartDetailsFragment.value = toggle
    }

    // Get all estates from REPO
    val allEstates: LiveData<List<Estate>> = estateRepository.getAllEstates().asLiveData()

    // ---------------
    // QUERIED ESTATE
    // ---------------
    // Observed to pass selected Estate to Details Fragment onClick
    val selectedEstateId: LiveData<Long?> get() = _selectedEstateId
    private val _selectedEstateId = MutableLiveData<Long?>(null)
    fun updateSelectedEstateId(estateId: Long?){
        _selectedEstateId.value = estateId
    }

    val queriedEstate: LiveData<Estate> get() = _queriedEstate
    private val _queriedEstate = MutableLiveData<Estate>()
    private fun updateQueriedEstate(estate: Estate){
        _queriedEstate.value = estate
    }

    fun getEstate(estateId: Long){
        viewModelScope.launch {
            estateRepository.getEstateWithId(estateId).collect{
                updateQueriedEstate(it)
            }
        }
    }

    // ------------------
    // SECONDARY PICTURES
    // ------------------

    // SECONDARY PICTURES
    val secondaryPicturesForCurrentEstate: LiveData<List<Picture>> get() = _secondaryPicturesForCurrentEstate
    private val _secondaryPicturesForCurrentEstate = MutableLiveData(RANDOM_PICTURES)
    private fun updateSecondaryPicturesForCurrentEstate(secondaryPictures: List<Picture>){
        _secondaryPicturesForCurrentEstate.value = secondaryPictures
    }

    fun getSecondaryPicturesByEstateId(estateId: Long){
        viewModelScope.launch {
            pictureRepository.getAllPicturesForGivenEstateId(estateId).collect {
                updateSecondaryPicturesForCurrentEstate(it)
            }
        }
    }

    // ------------
    // FETCH AGENT
    // -----------

    val agentId: LiveData<Long?> get() = _agentId
    private val _agentId = MutableLiveData<Long?>(null)
    fun updateAgentId(agentId: Long?){
        _agentId.value = agentId
    }
    val agent: LiveData<Agent> get() = _agent
    private val _agent = MutableLiveData<Agent>()
    private fun updateAgent(agent: Agent){
        _agent.value = agent
    }

    fun getAgentById(agentId: Long?) =
        viewModelScope.launch {
            agentRepository.getAgentById(agentId).collect{
                updateAgent(it)
            }
        }

    // -----------
    // SEARCH QUERY
    // ------------

    /// QUERY
    val searchQuery: StateFlow<Search> get() = _searchQuery
    private val _searchQuery = MutableStateFlow(Search())
    fun updateSearchQuery(search: Search){
        _searchQuery.value = search
    }

    // QUERY DEBUG (TEST)
    val searchQueryTest: StateFlow<String> get() = _searchQueryTest
    private val _searchQueryTest = MutableStateFlow("")
    fun updateSearchQueryTest(query: String){
        _searchQueryTest.value = query
    }

    val estates: StateFlow<List<Estate>> get() = _estates
    private val _estates = MutableStateFlow<List<Estate>>(emptyList())
    private fun updateEstates(estates: List<Estate>){
        _estates.value = estates
    }

    fun getEstatesWithQuery(simpleSQLiteQuery: SimpleSQLiteQuery) {
        viewModelScope.launch {
            // CHECK REQUIRED BECAUSE EMPTY SimpleSQLiteQuery WILL CRASH THE APP
            if (simpleSQLiteQuery.sql.isNotEmpty()) {
                estateRepository
                    .getSearchedEstates(simpleSQLiteQuery)
                    .collect { updateEstates(it) }
            }else {
                println("EMPTY QUERY")
            }
        }
    }

    val toggleSearchButton: LiveData<Boolean> get() = _toggleSearchButton
    private val _toggleSearchButton = MutableLiveData(false)
    fun updateToggleSearchButton(boolean: Boolean){
        _toggleSearchButton.value = boolean
    }
    fun onClickSearchButton(){
        if (toggleSearchButton.value == true) updateToggleSearchButton(false)
        else updateToggleSearchButton(true)
    }

    // -----------------
    // onClick Edit/Sell
    // -----------------

    // EDIT
    val estateToEdit: LiveData<Estate?> get() = _estateToEdit
    private val _estateToEdit = MutableLiveData<Estate?>()
    fun updateEstateToEdit(estate: Estate?){
        _estateToEdit.value = estate
    }
    fun onClickEditButton(){
        updateEstateToEdit(queriedEstate.value)
    }

    // SELL
    val wasSellButtonClicked: LiveData<Boolean> get() = _wasSellButtonClicked
    private val _wasSellButtonClicked = MutableLiveData(false)
    private fun updateWasSellButtonClicked(boolean: Boolean){
        _wasSellButtonClicked.value = boolean
    }

    fun onClickSellButton(){
        updateWasSellButtonClicked(true)
        // Reset
        updateWasSellButtonClicked(false)
    }

    fun updateEstateIntoDatabase(soldDateInMillis: Long) =
        viewModelScope.launch {
            val currentEstate: Estate? = queriedEstate.value
            currentEstate?.wasSold = true
            currentEstate?.soldDate = soldDateInMillis
            currentEstate?.let { estateRepository.updateEstate(currentEstate) }
        }

    // -------------------
    // onClick: Refresh DB
    // -------------------
    val databaseRefreshed: LiveData<Boolean> get() = _databaseRefreshed
    private val _databaseRefreshed = MutableLiveData(false)
    fun onClickDatabaseRefreshButton(){
        _databaseRefreshed.value = true
    }

    // --------------------
    // onClick SEARCH DATE
    // --------------------

    // START ADDED DATE
    val startAddedDate: LiveData<Long?> get() = _startAddedDate
    private val _startAddedDate = MutableLiveData<Long?>(null)
    fun updateStartAddedDate(dateInMillis: Long?){
        _startAddedDate.value = dateInMillis
    }

    // END ADDED DATE
    val endAddedDate: LiveData<Long?> get() = _endAddedDate
    private val _endAddedDate = MutableLiveData<Long?>(null)
    fun updateEndAddedDate(dateInMillis: Long?){
        _endAddedDate.value = dateInMillis
    }

    // START SOLD DATE
    val startSoldDate: LiveData<Long?> get() = _startSoldDate
    private val _startSoldDate = MutableLiveData<Long?>(null)
    fun updateStartSoldDate(dateInMillis: Long?){
        _startSoldDate.value = dateInMillis
    }

    // END SOLD DATE
    val endSoldDate: LiveData<Long?> get() = _endSoldDate
    private val _endSoldDate = MutableLiveData<Long?>(null)
    fun updateEndSoldDate(dateInMillis: Long?){
        _endSoldDate.value = dateInMillis
    }

    // IDENTIFY WHICH DATE WAS CLICKED
    val dateType: LiveData<Int> get() = _dateType
    private val _dateType = MutableLiveData<Int>()
    private fun updateDateType(value: Int){
        _dateType.value = value
    }

    // ON CLICK
    fun onClickDateButton(value: Int){
        updateDateType(value)
    }

    // TOGGLE SOLD DATE VISIBILITY
    val soldDateVisibility: LiveData<Boolean> get() = _soldDateVisibility
    private val _soldDateVisibility = MutableLiveData<Boolean>()
    fun updateSoldDateVisibility(boolean: Boolean){
        _soldDateVisibility.value = boolean
    }

    // RESET BUTTONS
    fun resetDateValues(from: Int){
        when (from){
            1 -> {
                updateStartAddedDate(null)
                updateEndAddedDate(null)
            }
            2 -> {
                updateStartSoldDate(null)
                updateEndSoldDate(null)
            }
        }
    }

}