package com.oxymium.realestatemanager.features.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.oxymium.realestatemanager.ESTATE_TYPES
import com.oxymium.realestatemanager.NEARBY_PLACES
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.SECONDARY_PICTURES_AMOUNT_LIMIT
import com.oxymium.realestatemanager.database.AgentRepository
import com.oxymium.realestatemanager.database.EstateRepository
import com.oxymium.realestatemanager.database.PictureRepository
import com.oxymium.realestatemanager.model.Agent
import com.oxymium.realestatemanager.model.Estate
import com.oxymium.realestatemanager.model.Label
import com.oxymium.realestatemanager.model.Picture
import com.oxymium.realestatemanager.toConcatenatedString
import com.oxymium.realestatemanager.toLabelList
import com.oxymium.realestatemanager.toLabelListTest
import kotlinx.coroutines.launch

// ---------------
// CreateViewModel
// ---------------
class CreateViewModel(private val agentRepository: AgentRepository, private val estateRepository: EstateRepository, private val pictureRepository: PictureRepository): ViewModel() {

    // --------------
    // EDIT OR CREATE
    // --------------

    val mode: LiveData<Int> get() = _mode
    private val _mode = MutableLiveData<Int>()
    fun updateMode(mode: Int){
        _mode.value = mode
    }

    // --------------
    // EDITED ESTATE
    // -------------

    val editedEstate: LiveData<Estate?> get() = _editedEstate
    private val _editedEstate = MutableLiveData<Estate?>(null)
    fun updateEditedEstate(estate: Estate?){
        _editedEstate.value = estate
    }

    // 「UPDATE」Prelaod all values into the fields
    fun preloadAllFieldsWithEstateToEditValues(estate: Estate){
        with(estate){
            updateSelectedAgentId(agent_id)
            updateSelectedType(type)
            updatePrice(price)
            updateSurface(surface)
            updateRooms(rooms)
            updateBedrooms(bedrooms)
            updateBathrooms(bathrooms)
            updateEnergyScore(energyScore)
            updateMainPicturePath(mainPicturePath)
            updateDescription(description)
            updateHighSpeedInternet(highSpeedInternet)
            updateFurnished(furnished)
            updateGarden(garden)
            updateDisabledAccessibility(disabledAccessibility)
            updateSelectedNearbyPlaces(nearbyPlaces.toLabelListTest())
            updateStreet(street)
            updateZipCode(zipCode)
            updateLocation(location)
            // Query secondary Pictures
            getPicturesForGivenEstateId(id)
        }
    }

    // -------------------
    // FRAGMENT NAVIGATION
    // -------------------
    val selectedStep: LiveData<Int?> get() = _selectedStep
    private val _selectedStep: MutableLiveData<Int?> = MutableLiveData<Int?>(null)
    fun updateSelectedStep(value: Int?){
        _selectedStep.value = when (value){
            null -> R.id.stepOneFragment
            0 -> R.id.stepOneFragment
            1 -> R.id.stepOneFragment
            2 -> R.id.stepTwoFragment
            3 -> R.id.stepThreeFragment
            4 -> R.id.stepFourFragment
            5 -> R.id.stepFiveFragment
            6 -> R.id.stepSixFragment
            else -> R.id.stepOneFragment
        }
    }

    val currentStep: LiveData<Int> get() = _currentStep
    private val _currentStep = MutableLiveData<Int>()
    fun updateCurrentStep(value: Int){
        _currentStep.value = value
    }

    // -----
    // MISC.
    // -----

    // AVAILABILITY
    val availability: LiveData<Boolean> get() = _availability
    private val _availability = MutableLiveData<Boolean>()
    fun updateAvailability(availability: Boolean){
        _availability.value = availability
    }

    // AVAILABLE SINCE
    val availableSince: LiveData<Long> get() = _availableSince
    private val _availableSince = MutableLiveData<Long>()
    fun updateAvailabilitySince(availabilitySince: Long){
        _availableSince.value = availabilitySince
    }

    // PURCHASE DATE
    val purchaseDate: LiveData<Long> get() = _purchaseDate
    private val _purchaseDate = MutableLiveData<Long>()
    fun updatePurchaseDate(purchaseDate: Long){
        _purchaseDate.value = purchaseDate
    }

    // --------
    // STEP ONE
    // --------

    // SELECTED AGENT
    val selectedAgentId: LiveData<Long?> get() = _selectedAgentId
    private val _selectedAgentId = MutableLiveData<Long?>(null)
    fun updateSelectedAgentId(agentId: Long?){
        if (agentId == selectedAgentId.value) _selectedAgentId.value = null
        else _selectedAgentId.value = agentId
    }

    // AGENTS
    val agentsFromDatabase = agentRepository.allAgents.asLiveData()
    val agents: LiveData<List<Agent>?> get() = _agents
    private val _agents = MutableLiveData<List<Agent>?>()
    fun updateAgents(agents: List<Agent>?) {
        _agents.value = agents
    }

    // SELECTED TYPE
    val selectedType: LiveData<String?> get() = _selectedType
    private val _selectedType = MutableLiveData<String?>(null)
    fun updateSelectedType(type: String?){
        _selectedType.value = type
    }


    // TYPES
    val types: LiveData<List<Label>> get() = _types
    private val _types = MutableLiveData(ESTATE_TYPES)
    fun updateTypes(types: List<Label>?) {
        _types.value = types
    }

    // ------
    // STEP 2
    // ------

    // PRICE
    val price: LiveData<Int?> get() = _price
    private val _price = MutableLiveData<Int?>()
    fun updatePrice(price: Int?){
        _price.value = price
    }

    // SURFACE
    val surface: LiveData<Int?> get() = _surface
    private val _surface = MutableLiveData<Int?>()
    fun updateSurface(surface: Int?){
        _surface.value = surface
    }

    // ROOMS
    val rooms: LiveData<Int?> get() = _rooms
    private val _rooms = MutableLiveData<Int?>()
    fun updateRooms(rooms: Int?){
        _rooms.value = rooms
    }

    // BEDROOMS
    val bedrooms: LiveData<Int?> get() = _bedrooms
    private val _bedrooms = MutableLiveData<Int?>()
    fun updateBedrooms(bedrooms: Int?){
        _bedrooms.value = bedrooms
    }

    // BATHROOMS
    val bathrooms: LiveData<Int?> get() = _bathrooms
    private val _bathrooms = MutableLiveData<Int?>()
    fun updateBathrooms(bathrooms: Int?){
        _bathrooms.value = bathrooms
    }

    // ENERGY SCORE
    val energyScore: LiveData<Int?> get() = _energyScore
    private val _energyScore = MutableLiveData<Int?>()
    fun updateEnergyScore(energyScore: Int?){
        _energyScore.value = energyScore
    }

    // ---------------------
    // STEP 3 (MAIN PICTURE)
    // ---------------------

    val mainPicturePath: LiveData<String?> get() = _mainPicturePath
    private val _mainPicturePath = MutableLiveData<String?>()
    fun updateMainPicturePath(mainPicturePath: String?){
        _mainPicturePath.value = mainPicturePath
    }

    // ---------------------------
    // STEP 4 (SECONDARY PICTURES)
    // ---------------------------

    // LIST LIMIT
    val secondaryPicturesAmountLimit: LiveData<Int> get() = _secondaryPicturesAmountLimit
    private val _secondaryPicturesAmountLimit = MutableLiveData(SECONDARY_PICTURES_AMOUNT_LIMIT)

    // SECONDARY PICTURES
    val secondaryPictures: LiveData<List<Picture>?> get() = _secondaryPictures
    private val _secondaryPictures = MutableLiveData<List<Picture>?>()
    private fun updateSecondaryPictures(secondaryPictures: List<Picture>?){
        _secondaryPictures.value = secondaryPictures
    }

    private fun getPicturesForGivenEstateId(estateId: Long?){
        viewModelScope.launch {
            estateId?.let {
                pictureRepository.getAllPicturesForGivenEstateId(estateId).collect {
                    updateSecondaryPictures(it)
                }
            }
        }
    }

    // PREVIEW SECONDARY PICTURE
    val secondaryPicturePreview: LiveData<String?> get() = _secondaryPicturePreview
    private val _secondaryPicturePreview = MutableLiveData<String?>()
    fun updateSecondaryPicturePreview(secondaryPicturePreview: String?){
        _secondaryPicturePreview.value = secondaryPicturePreview
    }

    fun addPictureToSecondaryPictures(picture: Picture){
        val oldSecondaryPictures = secondaryPictures.value?.toMutableList() ?: mutableListOf()
        oldSecondaryPictures.add(picture)
        updateSecondaryPictures(oldSecondaryPictures)
    }
    fun deletePictureFromSecondaryList(picture: Picture) {
        val oldSecondaryPictures: MutableList<Picture> = secondaryPictures.value?.toMutableList() ?: mutableListOf()
        oldSecondaryPictures.remove(picture)
        updateSecondaryPictures(oldSecondaryPictures)
    }

    fun updateCommentFromSecondaryPictures(oldPicture: Picture, newPicture: Picture) {
        val oldSecondaryPictures: MutableList<Picture> = secondaryPictures.value?.toMutableList() ?: mutableListOf()
        oldSecondaryPictures.remove(oldPicture)
        oldSecondaryPictures.add(newPicture)
        updateSecondaryPictures(oldSecondaryPictures)
    }

    // ------
    // STEP 5
    // ------

    // DESCRIPTION
    val description: LiveData<String?> get() = _description
    private val _description = MutableLiveData<String?>()
    fun updateDescription(description: String?){
        _description.value = description
    }

    // INTERNET
    val highSpeedInternet: LiveData<Boolean> get() = _highSpeedInternet
    private val _highSpeedInternet = MutableLiveData<Boolean>()
    fun updateHighSpeedInternet(highSpeedInternet: Boolean){
        _highSpeedInternet.value = highSpeedInternet
    }

    // FURNISHED
    val furnished: LiveData<Boolean> get() = _furnished
    private val _furnished = MutableLiveData<Boolean>()
    fun updateFurnished(furnished: Boolean){
        _furnished.value = furnished
    }

    // GARDEN
    val garden: LiveData<Boolean> get() = _garden
    private val _garden = MutableLiveData<Boolean>()
    fun updateGarden(garden: Boolean){
        _garden.value = garden
    }

    // DISABLED ACCESSIBILITY
    val disabledAccessibility: LiveData<Boolean> get() = _disabledAccessibility
    private val _disabledAccessibility = MutableLiveData<Boolean>()
    fun updateDisabledAccessibility(disabledAccessibility: Boolean){
        _disabledAccessibility.value = disabledAccessibility
    }

    // ------
    // STEP 6
    // ------

    // STREET
    val street: LiveData<String?> get() = _street
    private val _street = MutableLiveData<String?>()
    fun updateStreet(street: String?){
        _street.value = street
    }

    // ZIPCODE
    val zipCode: LiveData<String?> get() = _zipCode
    private val _zipCode = MutableLiveData<String?>()
    fun updateZipCode(zipCode: String?){
        _zipCode.value = zipCode
    }

    // LOCATION
    val location: LiveData<String?> get() = _location
    private val _location = MutableLiveData<String?>()
    fun updateLocation(location: String?){
        _location.value = location
    }

    // SELECTED NEARBY PLACES
    val selectedNearbyPlaces: LiveData<List<String?>> get() = _selectedNearbyPlaces
    private val _selectedNearbyPlaces = MutableLiveData<List<String?>>(listOf())
    fun updateSelectedNearbyPlaces(nearbyPlaces: List<String>){
        val test = selectedNearbyPlaces.value?.toMutableList()
        test?.addAll(nearbyPlaces)
        _selectedNearbyPlaces.value = test?.toList()
    }

    fun updateSelectedNearbyPlaces(label: String?){
        val test = selectedNearbyPlaces.value?.toMutableList()
        if (test?.contains(label) == true) test.remove(label)
        else test?.add(label)
        _selectedNearbyPlaces.value = test?.toList()
    }

    fun updateTest(nearbyPlaces: String?){
        val test = selectedNearbyPlaces.value?.toMutableList()
        val test2 = nearbyPlaces?.toLabelListTest()?.toMutableList()
        if (test2?.contains(nearbyPlaces) == true) test2.remove(nearbyPlaces)
        else test2?.add(nearbyPlaces)
        _selectedNearbyPlaces.value = test2?.toList()
    }

    // NEARBY PLACES
    val nearbyPlaces: LiveData<List<Label>> get() = _nearbyPlaces
    private val _nearbyPlaces = MutableLiveData(NEARBY_PLACES)
    fun updateSelectedPlaces(nearbyPlaces: List<Label>){
        _nearbyPlaces.value = nearbyPlaces
    }

    // --------------
    // RANDOM VALUES
    // --------------

    // 1 = CAMERA, 2 = GALLERY
    val pictureActivityType: LiveData<Int> get() = _pictureActivityType
    private val _pictureActivityType = MutableLiveData<Int>()
    private fun updatePictureActivityType(pictureActivityType: Int){
        _pictureActivityType.value = pictureActivityType
    }

    val isEstateDoneBeingCreatedOrEdited: LiveData<Boolean?> get() = _isEstateDoneBeingCreatedOrEdited
    private val _isEstateDoneBeingCreatedOrEdited = MutableLiveData<Boolean?>(null)
    private fun toggleIsEstateDoneBeingCreatedOrEdited(boolean: Boolean?){
        _isEstateDoneBeingCreatedOrEdited.value = boolean
    }

    val triggerSaveAlertDialog: LiveData<Boolean?> get() = _triggerSaveAlertDialog
    private val _triggerSaveAlertDialog = MutableLiveData<Boolean?>()
    fun toggleSaveAlertDialog(toggle: Boolean?){
        _triggerSaveAlertDialog.value = toggle
    }


    // -------------
    // NOTIFICATIONS
    // -------------

    val notificationId: LiveData<Long> get() = _notificationId
    private val _notificationId = MutableLiveData<Long>()
    private fun updateNotificationId(id: Long) { _notificationId.value = id }


    // onClick to add Main Picture
    fun onClickAddMainPictureButton(){ updatePictureActivityType(1) }

    fun onClickDeleteMainPictureButton(){ updateMainPicturePath("") }

    // onClick to add Pictures to the RecyclerView
    fun onClickAddSecondaryPictureButton(){ updatePictureActivityType(2) }

    // onClick to SAVE
    fun onClickSaveEstateButton(){ verifyIfEstateCanBeSaved() }

    // Insert Estate into DB
    fun insertEstateIntoDatabase() =
        viewModelScope.launch {
            val insertedId: Long = estateRepository.insert(
                Estate(
                    availableSince.value ?: 0L,
                    availability.value ?: false,
                    purchaseDate.value ?: 0L,
                    selectedType.value ?: "",
                    price.value ?: 0,
                    energyScore.value ?: 0,
                    surface.value ?: 0,
                    rooms.value ?: 0,
                    bedrooms.value ?: 0,
                    bathrooms.value ?: 0,
                    street.value.toString(),
                    zipCode.value.toString(),
                    location.value.toString(),
                    highSpeedInternet.value ?: false,
                    furnished.value ?: false,
                    disabledAccessibility.value ?: false,
                    garden.value ?: false,
                    // TODO fix nearbyPlaces
                    selectedNearbyPlaces.value.toString().toLabelList().toConcatenatedString(),
                    description.value.toString(),
                    mainPicturePath.value.toString(),
                    1L
                ))

            // Attach returned Estate ID to all Pictures
            if (!secondaryPictures.value.isNullOrEmpty()) {

                for (i in 0 until secondaryPictures.value!!.size) {
                    secondaryPictures.value!![i].estate_id = insertedId
                }
                // Insert Pictures into DB
                for (i in 0 until secondaryPictures.value!!.size) {
                    pictureRepository.insert(secondaryPictures.value!![i])
                }
            }

            updateNotificationId(insertedId)
            clearCreateFields()
            toggleIsEstateDoneBeingCreatedOrEdited(true)
        }

    // Update Estate into DB
    fun updateEstateIntoDatabase() =
        viewModelScope.launch {
            val estate = editedEstate.value
            estate?.let {
                it.agent_id = selectedAgentId.value ?: 0
                it.type = selectedType.value ?: ""
                it.price = price.value ?: 0
                it.surface = surface.value ?: 0
                it.rooms = rooms.value ?: 0
                it.bedrooms = bedrooms.value ?: 0
                it.bathrooms = bathrooms.value ?: 0
                it.energyScore = energyScore.value ?: 0
                it.mainPicturePath = mainPicturePath.value ?: ""
                it.description = description.value ?: ""
                it.highSpeedInternet = highSpeedInternet.value ?: false
                it.furnished = furnished.value ?: false
                it.garden = garden.value ?: false
                it.disabledAccessibility = disabledAccessibility.value ?: false
                it.street = street.value ?: ""
                it.zipCode = zipCode.value ?: ""
                it.location = location.value ?: ""
            }?.also{
                estateRepository.updateEstate(estate)
                toggleIsEstateDoneBeingCreatedOrEdited(true)
            }
        }

    // -----------
    // SAVE CHECK
    // -----------
    val missingElementsAsStrings: LiveData<String?> get() = _missingElementsAsStrings
    private val _missingElementsAsStrings = MutableLiveData<String?>()
    private fun updateMissingElementsAsString(elements: String?){
        _missingElementsAsStrings.value = elements
    }

    private fun verifyIfEstateCanBeSaved(){
        if (
            selectedAgentId.value == null ||
            selectedType.value == null ||
            price.value == null ||
            surface.value == null ||
            rooms.value == null ||
            bedrooms.value == null ||
            bathrooms.value == null ||
            energyScore.value == null ||
            mainPicturePath.value == null ||
            secondaryPictures.value == null ||
            description.value == null ||
            street.value == null ||
            zipCode.value == null ||
            location.value== null
        ){
            val nullElements = mutableListOf<String?>()
            when (selectedAgentId.value) { null -> nullElements.add("Agent") }
            when (selectedType.value) { null -> nullElements.add("Value") }
            when (price.value) { null -> nullElements.add("Price") }
            when (surface.value) { null -> nullElements.add("Surface") }
            when (rooms.value) { null -> nullElements.add("Rooms") }
            when (bathrooms.value) { null -> nullElements.add("Bathrooms") }
            when (energyScore.value) { null -> nullElements.add("Energy score") }
            when (mainPicturePath.value) { null -> nullElements.add("Main picture") }
            when (secondaryPictures.value) { null -> nullElements.add("Secondary Pictures") }
            when (description.value) { null -> nullElements.add("Description") }
            when (street.value) { null -> nullElements.add("Street") }
            when (zipCode.value) { null -> nullElements.add("Zip Code") }
            when (location.value) { null -> nullElements.add("Location") }

            updateMissingElementsAsString(nullElements.toList().toString())

        }else{
            updateMissingElementsAsString(null)
        }
    }

    // -----
    // CLEAR
    // -----

    private fun clearCreateFields(){
        updateSelectedAgentId(null)
        updateSelectedType(null)
        updatePrice(null)
        updateSurface(null)
        updateRooms(null)
        updateBedrooms(null)
        updateBathrooms(null)
        updateEnergyScore(null)
        updateMainPicturePath(null)
        updateSecondaryPictures(null)
        updateDescription(null)
        updateHighSpeedInternet(false)
        updateFurnished(false)
        updateGarden(false)
        updateDisabledAccessibility(false)
        updateSelectedNearbyPlaces(null)
        updateStreet(null)
        updateZipCode(null)
        updateLocation(null)
    }

    // ------
    // DEBUG
    // -----

    fun fillCreateWithOneRandomEstate(estate: Estate){
        with(estate){
            updateSelectedAgentId(agent_id)
            updateSelectedType(type)
            updatePrice(price)
            updateSurface(surface)
            updateRooms(rooms)
            updateBedrooms(bedrooms)
            updateBathrooms(bathrooms)
            updateEnergyScore(energyScore)
            updateMainPicturePath(mainPicturePath)
            updateSecondaryPictures(listOf(Picture("", "Room", 0, 0)))
            updateDescription(description)
            updateHighSpeedInternet(highSpeedInternet)
            updateFurnished(furnished)
            updateGarden(garden)
            updateDisabledAccessibility(disabledAccessibility)
            updateStreet(street)
            updateZipCode(zipCode)
            updateLocation(location)
            updateSelectedNearbyPlaces(nearbyPlaces)
        }
    }
}