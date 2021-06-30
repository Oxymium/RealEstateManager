package com.oxymium.realestatemanager.viewmodel

import androidx.lifecycle.*
import com.oxymium.realestatemanager.database.EstateRepository
import com.oxymium.realestatemanager.model.Estate
import io.reactivex.Completable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

// ---------------
// EstateViewModel
// ---------------

class EstateViewModel(private val estateRepository: EstateRepository): ViewModel() {

    // Observed to pass selected Estate to Details Fragment onClick
    var selectedEstate: MutableLiveData<Estate> = MutableLiveData(null)

    // Get all estates from REPO
    val allEstates: LiveData<List<Estate>> = estateRepository.allEstates.asLiveData()

    @ExperimentalCoroutinesApi
    private val searchChanel = ConflatedBroadcastChannel<String>()

    @ExperimentalCoroutinesApi
    val estateListLiveData = searchChanel.asFlow()
        .flatMapLatest { search ->
            // We use flatMapLatest as we don't want flows of flows and we only want to query the latest searched string in case user types
            // in a new query before the earlier one is finished processing.
            estateRepository.getSearchedEstate(search)
        }
        .catch { throwable ->
            // ERROR
        }.asLiveData()

    @ExperimentalCoroutinesApi
    fun setQuickSearchQuery(quickSearch: String) {
        searchChanel.offer(quickSearch)
    }

    fun insert(estate: Estate) = viewModelScope.launch {
        estateRepository.insert(estate)
    }

    // Create a random Estate for test purposes
    fun onClickAddOneButton(){

        var cityNames = arrayOf("Paris", "Tokyo", "New-York", "Singapore")
        var randomCityName = cityNames.random()

        var estateType = arrayOf("Bungalow", "Condominium", "Cottage", "Duplex", "Farmhouse", "Flat", "Lodge", "Manor", "Penthouse", "Townhouse", "Villa")
        var randomType = estateType.random()

        var price = arrayOf(100000, 150000, 550200, 660500, 1000000, 2000000, 2500000)
        var randomPrice = price.random()

        var energy = arrayOf("A+", "A", "B", "C", "D")
        var randomEnergy = energy.random()

        var randomSurface = (0..200).random()
        var randomRooms = (2..20).random()

        var randomEstate = Estate(0, randomType, randomPrice, randomSurface, randomEnergy, randomRooms, randomCityName)

        viewModelScope.launch {
            estateRepository.insert(randomEstate)
        }

    }

    fun onClickDeleteAllButton(){ viewModelScope.launch {
            estateRepository.deleteAll()
        }
    }

}