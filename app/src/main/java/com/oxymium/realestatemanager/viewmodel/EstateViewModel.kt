package com.oxymium.realestatemanager.viewmodel

import android.util.Log
import androidx.lifecycle.*
import androidx.room.RawQuery
import androidx.sqlite.db.SimpleSQLiteQuery
import com.oxymium.realestatemanager.database.EstateRepository
import com.oxymium.realestatemanager.database.PictureRepository
import com.oxymium.realestatemanager.model.Estate
import com.oxymium.realestatemanager.model.Picture
import io.reactivex.Completable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import com.bumptech.glide.Glide

import android.R
import android.graphics.PorterDuffColorFilter
import android.widget.TextView
import androidx.core.app.NotificationCompat.getColor

import androidx.databinding.BindingAdapter
import android.graphics.PorterDuff

import android.R.color
import android.graphics.Color

import androidx.core.content.ContextCompat

import android.graphics.drawable.Drawable







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

    // Query channels
    @ExperimentalCoroutinesApi
    private val searchChannel = ConflatedBroadcastChannel<SimpleSQLiteQuery>()

    @ExperimentalCoroutinesApi
    //val estateListLiveData = quickSearchChannel.asFlow()
    val estateListLiveData = searchChannel.asFlow()
    .flatMapLatest { search ->
            // We use flatMapLatest as we don't want flows of flows and we only want to query the latest searched string in case user types
            // in a new query before the earlier one is finished processing.
        //estateRepository.getSearchedEstate(search)
        estateRepository.getSearchedEstates(search) }
        .catch { throwable ->

        }.asLiveData()

    @ExperimentalCoroutinesApi
    fun setQuickSearchQuery(quickSearch: String) {
        val quickSearchValue = "SELECT * FROM estate WHERE location LIKE '%$quickSearch%' OR type LIKE '%$quickSearch%' OR price LIKE '%$quickSearch%' OR surface LIKE '%$quickSearch%' OR energyScore like '%$quickSearch%'"
        val simpleSQLiteQuery = SimpleSQLiteQuery(quickSearchValue)
        searchChannel.offer(simpleSQLiteQuery)
    }

    @ExperimentalCoroutinesApi
    fun setSearchQuery(search: String) {
        val quickSearchValue = "SELECT * FROM estate WHERE price >= $search "
        val simpleSQLiteQuery = SimpleSQLiteQuery(quickSearchValue)
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

    // SELECT * FROM estate WHERE location LIKE '%' || :search || '%' OR type LIKE '%' || :search || '%'

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

    fun updateEstateIntoDatabase(soldDate: String) = viewModelScope.launch {
        val oldEstate: Estate? = selectedEstate.value
        oldEstate?.wasSold = true
        oldEstate?.soldDate = soldDate
        selectedEstate.postValue(oldEstate)
        estateRepository.updateEstate(oldEstate!!)
        }

}