package com.oxymium.realestatemanager.viewmodel

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.sqlite.db.SimpleSQLiteQuery
import com.oxymium.realestatemanager.RANDOM_PICTURES
import com.oxymium.realestatemanager.database.AgentRepository
import com.oxymium.realestatemanager.database.EstateRepository
import com.oxymium.realestatemanager.database.PictureRepository
import com.oxymium.realestatemanager.model.Agent
import com.oxymium.realestatemanager.model.Estate
import com.oxymium.realestatemanager.model.Picture
import com.oxymium.realestatemanager.model.Search
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
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

    // Query state
    var queryValue: MutableLiveData<String> = MutableLiveData("")

    // Get all estates from REPO
    val allEstates: LiveData<List<Estate>> = estateRepository.allEstates.asLiveData()

    // Store estate list size
    var estatesListSize: MutableLiveData<Int> = MutableLiveData(0)

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



    // -------------------------------------------
    // TODO: REFACTOR DEPRECATED BROADCAST CHANNEL
    // SEARCH QUERY
    // ------------------------------------------

    val searchQuery: MutableLiveData<Search> = MutableLiveData(Search())

    // Query channels
    @ExperimentalCoroutinesApi
    private val searchChannel = ConflatedBroadcastChannel<SimpleSQLiteQuery>()

    @ExperimentalCoroutinesApi
    val estateListLiveData = searchChannel.asFlow()
        .flatMapLatest { search ->
            // We use flatMapLatest as we don't want flows of flows and we only want to query the latest searched string in case user types
            // in a new query before the earlier one is finished processing.
            estateRepository.getSearchedEstates(search) }
        .catch { throwable -> println("Something went wrong with Estate search")

        }.asLiveData()


    @ExperimentalCoroutinesApi
    fun setSearchQuery(search: String, from: Int) {
        var searchValue: String? = null
        // Quick search
        if (from == 1){
            searchValue = "SELECT * FROM estate WHERE location LIKE '%$search%' OR type LIKE '%$search%' OR price LIKE '%$search%' OR surface LIKE '%$search%' OR energyScore like '%$search%'"
        }
        // Pre-formatted search
        if (from == 2){
            searchValue = search
        }
        val simpleSQLiteQuery = searchValue?.let { SimpleSQLiteQuery(it) }
        println("SEARCH $search")
        if (simpleSQLiteQuery != null) {
            searchChannel.trySend(simpleSQLiteQuery)
        }
    }

    var searchButtonToggle: MutableLiveData<Int> = MutableLiveData(0)

    fun onClickSearchButton(){
        when (searchButtonToggle.value) {
            0 -> searchButtonToggle.value = 1
            1 -> searchButtonToggle.value = 0
        }
    }

    // -----------------
    // onClick Edit/Sell
    // -----------------
    val estateToEdit: LiveData<Estate?> get() = _estateToEdit
    private val _estateToEdit = MutableLiveData<Estate?>()
    fun updateEstateToEdit(estate: Estate?){
        _estateToEdit.value = estate
    }
    fun onClickEditButton(){
        updateEstateToEdit(queriedEstate.value)
    }

    var wasSellButtonClicked: MutableLiveData<Int> = MutableLiveData(0)
    fun onClickSellButton(){
        wasSellButtonClicked.postValue(1)
    }

    fun updateEstateIntoDatabase(soldDateInMillis: Long) =
        viewModelScope.launch {
            val currentEstate: Estate? = queriedEstate.value
            currentEstate?.wasSold = true
            currentEstate?.soldDate = soldDateInMillis
            currentEstate?.let { estateRepository.updateEstate(currentEstate) }
        }

    // --------------------
    // onClick Search dates
    // --------------------

    var startingAddedDate: MutableLiveData<Long> = MutableLiveData()
    var endingAddedDate: MutableLiveData<Long> = MutableLiveData()
    // 0 = default, 1 = StartingDate, 2 = EndingDate, 3 = StartingDateSold, 4 = EndingDateSold
    var wasDateClicked: MutableLiveData<Int> = MutableLiveData(0)

    fun onClickStartingDateButton(){
        wasDateClicked.value = 1
    }

    fun onClickEndingDateButton(){
        wasDateClicked.value = 2
    }

    var startingSoldDate: MutableLiveData<Long> = MutableLiveData()
    var endingSoldDate: MutableLiveData<Long> = MutableLiveData()

    fun onClickStartingDateSoldButton(){
        wasDateClicked.value = 3
    }

    fun onClickEndingDateSoldButton(){
        wasDateClicked.value = 4
    }

    // MAP

    val addresses: LiveData<List<Address>> get() = _addresses
    private val _addresses = MutableLiveData<List<Address>>()
    fun updateAddresses(addresses: List<Address>){
        _addresses.value = addresses
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun flowGeocodeTest(context: Context, fusedAddress: String, maxResults: Int): Flow<List<Address>> = callbackFlow {
        val geocoder = Geocoder(context)
        val listener = object : Geocoder.GeocodeListener{
            override fun onGeocode(addresses: MutableList<Address>) {
                try {
                    if (!isClosedForSend) trySend(addresses).isSuccess
                }catch (e: Exception){
                    close(e)
                }
            }
            override fun onError(errorMessage: String?) {
                // Handle error
            }
        }
        geocoder.getFromLocationName(fusedAddress, 1, listener)
        awaitClose()
    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun getLatLngFromEstates(context: Context, fusedAddress: String){
        viewModelScope.launch {
            flowGeocodeTest(context, fusedAddress, 1).collect{
                updateAddresses(it)
            }
        }
    }


}