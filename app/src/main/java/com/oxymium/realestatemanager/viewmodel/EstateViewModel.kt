package com.oxymium.realestatemanager.viewmodel

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import androidx.sqlite.db.SimpleSQLiteQuery
import com.oxymium.realestatemanager.database.EstateRepository
import com.oxymium.realestatemanager.database.PictureRepository
import com.oxymium.realestatemanager.model.Estate
import com.oxymium.realestatemanager.model.Picture
import com.oxymium.realestatemanager.model.Search
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch


// ---------------
// EstateViewModel
// ---------------

class EstateViewModel(private val estateRepository: EstateRepository, private val pictureRepository: PictureRepository): ViewModel() {

    val isTablet: MutableLiveData<Boolean> = MutableLiveData(false)

    // Navigation 1 = from Maps, 2 = from Estates
    val startDetailsFragmentFrom: MutableLiveData<Int> = MutableLiveData(0)

    // Query state
    var queryValue: MutableLiveData<String> = MutableLiveData("")

    // Get all estates from REPO
    val allEstates: LiveData<List<Estate>> = estateRepository.allEstates.asLiveData()

    // Store estate list size
    var estatesListSize: MutableLiveData<Int> = MutableLiveData(0)

    // Observed to pass selected Estate to Details Fragment onClick
    var selectedEstate: MutableLiveData<Estate> = MutableLiveData(null)

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
        .catch { throwable -> println("Something went wrong")

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
        // TODO add all fields
        //val quickSearchValueTEST = "SELECT *, COUNT(picture.id) AS nbPics FROM estate LEFT JOIN picture ON estate.id = picture.estate_id GROUP BY estate.id HAVING nbPics > 0 AND price > 1000000"
        //val quickSearchValue = "SELECT *, COUNT(picture.id) AS nbPics FROM estate LEFT JOIN picture ON estate.id = picture.estate_id GROUP BY estate.id HAVING nbPics = 3"

        val simpleSQLiteQuery = SimpleSQLiteQuery(searchValue)
        println("SEARCH $search")
        searchChannel.offer(simpleSQLiteQuery)
    }

    var searchButtonToggle: MutableLiveData<Int> = MutableLiveData(0)

    fun onClickSearchButton(){
        when (searchButtonToggle.value) {
            0 -> searchButtonToggle.postValue(1)
            1 -> searchButtonToggle.postValue(0)
        }
    }

    // Fetch pictures for current Estate
    var allPicturesForCurrentEstate: LiveData<List<Picture>> = pictureRepository.provideTestPictures().asLiveData()

    // Get estate with ID for Maps (onClickMarker)
    fun getEstateFromId(estate: Estate){
        selectedEstate.postValue(estate)
        allPicturesForCurrentEstate = pictureRepository.getAllPicturesForGivenEstateId(estate.id!!).asLiveData()
        startDetailsFragmentFrom.postValue(2)
    }

    // -----------------
    // onClick Edit/Sell
    // -----------------

    var estateToEdit: MutableLiveData<Estate> = MutableLiveData()
    fun onClickEditButton(){
        estateToEdit.postValue(selectedEstate.value)
    }

    var wasSellButtonClicked: MutableLiveData<Int> = MutableLiveData(0)
    fun onClickSellButton(){
        wasSellButtonClicked.postValue(1)
    }

    fun updateEstateIntoDatabase(soldDateInMillis: Long) = viewModelScope.launch {
        val oldEstate: Estate? = selectedEstate.value
        oldEstate?.wasSold = true
        oldEstate?.soldDate = soldDateInMillis
        selectedEstate.postValue(oldEstate)
        estateRepository.updateEstate(oldEstate!!)
    }

    // --------------------
    // onClick Search dates
    // --------------------

    var startingAddedDate: MutableLiveData<Long> = MutableLiveData()
    var endingAddedDate: MutableLiveData<Long> = MutableLiveData()
    // 0 = default, 1 = StartingDate, 2 = EndingDate, 3 = StartingDateSold, 4 = EndingDateSold
    var wasDateClicked: MutableLiveData<Int> = MutableLiveData(0)

    fun onClickStartingDateButton(){
        wasDateClicked.postValue(1)
    }

    fun onClickEndingDateButton(){
        wasDateClicked.postValue(2)
    }

    var startingSoldDate: MutableLiveData<Long> = MutableLiveData()
    var endingSoldDate: MutableLiveData<Long> = MutableLiveData()

    fun onClickStartingDateSoldButton(){
        wasDateClicked.postValue(3)
    }

    fun onClickEndingDateSoldButton(){
        wasDateClicked.postValue(4)
    }


}