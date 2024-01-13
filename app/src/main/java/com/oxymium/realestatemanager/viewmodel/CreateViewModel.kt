package com.oxymium.realestatemanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.oxymium.realestatemanager.CREATE_MENU_STEPS
import com.oxymium.realestatemanager.SECONDARY_PICTURES_AMOUNT_LIMIT
import com.oxymium.realestatemanager.database.agent.AgentRepository
import com.oxymium.realestatemanager.database.estate.EstateRepository
import com.oxymium.realestatemanager.database.picture.PictureRepository
import com.oxymium.realestatemanager.model.EstateField
import com.oxymium.realestatemanager.model.MenuStep
import com.oxymium.realestatemanager.model.NearbyPlace
import com.oxymium.realestatemanager.model.ReachedSide
import com.oxymium.realestatemanager.model.databaseitems.Estate
import com.oxymium.realestatemanager.model.databaseitems.Picture
import com.oxymium.realestatemanager.model.mock.ESTATE_TYPES
import com.oxymium.realestatemanager.model.mock.generateOneRandomPicture
import com.oxymium.realestatemanager.model.toConcatenatedString
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

// ---------------
// CreateViewModel
// ---------------
class CreateViewModel(
    private val agentRepository: AgentRepository,
    private val estateRepository: EstateRepository,
    private val pictureRepository: PictureRepository)
    : ViewModel() {

    // ------
    // ESTATE
    // ------
    val estate: LiveData<Estate?> get() = _estate
    private val _estate = MutableLiveData<Estate?>(null)
    fun updateEstate(estate: Estate?) {
        _estate.value = estate
    }

    // UPDATE ESTATE FIELDS
    fun updateEstateField(field: EstateField) {
        val currentEstate = estate.value?.copy() ?: Estate()
        when (field) {
            // STEP 0 - DATE
            is EstateField.Date -> currentEstate.addedDate = field.date
            // STEP 1 - AGENT ID / TYPE
            is EstateField.AgentId -> currentEstate.agent_id = field.agentId
            is EstateField.Type -> currentEstate.type = field.type
            // STEP 2 - VALUES / ENERGY SCORE & RATING
            is EstateField.Price -> currentEstate.price = field.price
            is EstateField.Surface -> currentEstate.surface = field.surface
            is EstateField.Rooms -> currentEstate.rooms = field.rooms
            is EstateField.Bedrooms -> currentEstate.bedrooms = field.bedrooms
            is EstateField.Bathrooms -> currentEstate.bathrooms = field.bathrooms
            is EstateField.EnergyScore -> currentEstate.energyScore = field.energyScore
            is EstateField.EnergyRating -> currentEstate.energyRating = field.energyRating
            // STEP 3 - MAIN PICTURE
            is EstateField.MainPicturePath -> currentEstate.mainPicturePath = field.mainPicturePath
            // STEP 5 - DESCRIPTION / MISCELLANEOUS
            is EstateField.Description -> currentEstate.description = field.description
            is EstateField.HighSpeedInternet -> currentEstate.highSpeedInternet = field.hasHighSpeedInternet
            is EstateField.Furnished -> currentEstate.furnished = field.isFurnished
            is EstateField.Garden -> currentEstate.garden = field.hasGarden
            is EstateField.DisabledAccessibility -> currentEstate.disabledAccessibility = field.hasDisabledAccessibility
            // STEP 6 - ADDRESS / LAT & LNG / NEARBY PLACES
            is EstateField.Street -> currentEstate.street = field.street
            is EstateField.ZipCode -> currentEstate.zipCode = field.zipCode
            is EstateField.Location -> currentEstate.location = field.location
            is EstateField.Latitude -> currentEstate.latitude = field.latitude
            is EstateField.Longitude -> currentEstate.longitude = field.longitude
            is EstateField.NearbyPlaces -> currentEstate.nearbyPlaces = field.nearbyPlaces
        }
        updateEstate(currentEstate)
    }

    // -------------------
    // FRAGMENT NAVIGATION
    // -------------------

    val currentCreateMenuStep: LiveData<MenuStep?> get() = _currentCreateMenuStep
    private val _currentCreateMenuStep = MutableLiveData<MenuStep?>(null)
    fun updateCurrentCreateMenuStep(createMenuStep: MenuStep) {
        _currentCreateMenuStep.value = createMenuStep
    }

    val createMenuSteps: LiveData<List<MenuStep>?> get() = _createMenuSteps
    private val _createMenuSteps = MutableLiveData(CREATE_MENU_STEPS)
    fun updateCreateMenuSteps(createMenuSteps: List<MenuStep>?) {
        _createMenuSteps.value = createMenuSteps
    }

    val createMenuStep: SharedFlow<MenuStep> get() = _createMenuStep
    private val _createMenuStep = MutableSharedFlow<MenuStep>(replay = 0)
    fun updateCreateMenuStep(createMenuStep: MenuStep) {
        viewModelScope.launch {
            _createMenuStep.emit(createMenuStep)
        }
    }

    val reachedStepSide: LiveData<ReachedSide> get() = _reachedSide
    // Initial value LEFT (recyclerView starts on first element)
    private val _reachedSide = MutableLiveData<ReachedSide>(ReachedSide.LeftSide)
    fun updateReachedStepSide(reachedSide: ReachedSide) {
        _reachedSide.value = reachedSide
    }

    val reachedAgentSide: LiveData<ReachedSide> get() = _reachedAgentSide
    private val _reachedAgentSide = MutableLiveData<ReachedSide>(ReachedSide.TopSide)
    fun updateReachedAgentSide(reachedSide: ReachedSide) {
        _reachedAgentSide.value = reachedSide
    }

    val reachedTypeSide: LiveData<ReachedSide> get() = _reachedTypeSide
    private val _reachedTypeSide = MutableLiveData<ReachedSide>(ReachedSide.TopSide)
    fun updateReachedTypeSide(reachedSide: ReachedSide) {
        _reachedTypeSide.value = reachedSide
    }

    val reachedNearbyPlacesSide: LiveData<ReachedSide> get() = _reachedNearbyPlacesSide
    private val _reachedNearbyPlacesSide = MutableLiveData<ReachedSide>(ReachedSide.TopSide)
    fun updateReachedNearbyPlacesSide(reachedSide: ReachedSide) {
        _reachedNearbyPlacesSide.value = reachedSide
    }

    // ----------
    // STEP AGENT
    // ----------
    fun updateSelectedAgent(agentId: Long) {
        viewModelScope.launch {
            _selectedAgentId.emit(agentId)
        }
    }

    // AGENTS
    private val _selectedAgentId = MutableStateFlow<Long?>(null)
    val combinedAgents = combine(
        agentRepository.getAllAgents(), _selectedAgentId, estate.asFlow()
    ) { agentsFromDatabase, selectedAgentId, currentEstate ->

        if (selectedAgentId != null) {
            updateEstateField(EstateField.AgentId(selectedAgentId))
            _selectedAgentId.value = null // reset value back to null
        }
        // update selected agent ID
        if (currentEstate?.agent_id != null) {
            agentsFromDatabase.map { agent ->
                if (agent.id == currentEstate.agent_id) {
                    // If agent's id matches currentEstate's agent_id, update isSelected
                    agent.copy(isSelected = !agent.isSelected)
                } else {
                    agent
                }
            }
        } else {
            agentsFromDatabase
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), emptyList())

    // ---------
    // STEP TYPE
    // ---------

    fun updateSelectedType(string: String) {
        viewModelScope.launch {
            _selectedType.emit(string)
        }
    }

    // TYPE
    private val _selectedType = MutableStateFlow<String?>(null)
    private val allTypes = MutableStateFlow(ESTATE_TYPES)
    val combinedTypes = combine(
        allTypes, _selectedType, estate.asFlow()
    ) { allTypes, selectedType, currentEstate ->

        if (selectedType != null) {
            updateEstateField(EstateField.Type(selectedType))
            _selectedType.value = null // reset value back to null
        }

        // update selected agent ID
        if (currentEstate?.agent_id != null) {
            allTypes.map { type ->
                if (type.label == currentEstate.type) {
                    // If agent's id matches currentEstate's agent_id, update isSelected
                    type.copy(isSelected = !type.isSelected)
                } else {
                    type
                }
            }
        } else {
            allTypes
        }

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), emptyList())

    // ------------------
    // STEP NEARBY PLACES
    // ------------------

    val nearbyPlaceStringValue: LiveData<String?> get() = _nearbyPlaceStringValue
    private val _nearbyPlaceStringValue = MutableLiveData<String?>(null)
    fun updateNearbyPlaceStringValue(stringValue: String?) {
        _nearbyPlaceStringValue.value = stringValue
    }

    val nearbyPlaces: LiveData<List<NearbyPlace>?> get() = _nearbyPlaces
    private val _nearbyPlaces = MutableLiveData<List<NearbyPlace>?>(emptyList())
    fun updateNearbyPlaces(nearbyPlaces: List<NearbyPlace>?) {
        _nearbyPlaces.value = nearbyPlaces
    }
    private fun addNearbyPlaceInNearbyPlaces(nearbyPlace: NearbyPlace) {
        val newList = nearbyPlaces.value?.toMutableList()
        newList?.add(nearbyPlace)
        newList?.let { updateNearbyPlaces(newList) }
    }

    fun deleteNearbyPlaceInNearbyPlaces(nearbyPlace: NearbyPlace) {
        val newList = nearbyPlaces.value?.toMutableList()
        newList?.remove(nearbyPlace)
        newList?.let { updateNearbyPlaces(newList) }
    }

    fun onClickAddNearbyPlaceButton() {
        if (!nearbyPlaceStringValue.value.isNullOrEmpty()) {
            val newNearbyPlace = NearbyPlace(content = nearbyPlaceStringValue.value)
            updateNearbyPlaceStringValue(null) // reset value after insertion
            addNearbyPlaceInNearbyPlaces(newNearbyPlace)
        }
    }

    // ---------------------------
    // STEP 4 (SECONDARY PICTURES)
    // ---------------------------

    // SECONDARY PICTURES
    val secondaryPictures: LiveData<List<Picture>?> get() = _secondaryPictures
    private val _secondaryPictures = MutableLiveData<List<Picture>?>()
    private fun updateSecondaryPictures(secondaryPictures: List<Picture>?){
        _secondaryPictures.value = secondaryPictures
    }

    fun getPicturesForGivenEstateId(estateId: Long?){
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

    fun addPictureToSecondaryPictures(picture: Picture) {
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
        val index = oldSecondaryPictures.indexOf(oldPicture) // get index position to replace the picture at the same position in the list
        if (index != -1) {
            val newSecondaryPictures =
                oldSecondaryPictures.toMutableList().apply { set(index, newPicture) }
            updateSecondaryPictures(newSecondaryPictures)
        }
    }

    // ---------------------------
    // STEP 6 - ADDRESS, LAT & LNG
    // ---------------------------

    // REVERSE GEOCODING (GET LAT & LNG FROM ADDRESS)
    val enableReverseGeoCoding: LiveData<Boolean?> get() = _enableReverseGeoCoding
    private val _enableReverseGeoCoding = MutableLiveData<Boolean?>()
    fun updateEnableReverseGeoCoding(boolean: Boolean){
        _enableReverseGeoCoding.value = boolean
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

    val shouldNavigateToEstatesFragment: SharedFlow<Boolean> get() = _shouldNavigateToEstatesFragment
    private val _shouldNavigateToEstatesFragment = MutableSharedFlow<Boolean>(0)
    private fun updateShouldNavigateToEstatesFragment(boolean: Boolean) {
        viewModelScope.launch {
            _shouldNavigateToEstatesFragment.emit(boolean)
        }
    }

    // -------------
    // NOTIFICATIONS
    // -------------

    val notificationId: LiveData<Long> get() = _notificationId
    private val _notificationId = MutableLiveData<Long>()
    private fun updateNotificationId(id: Long) { _notificationId.value = id }


    // onClick to add Main Picture
    fun onClickAddMainPictureButton(){ updatePictureActivityType(1) }

    fun onClickDeleteMainPictureButton(){ updateEstateField(EstateField.MainPicturePath(null) ) }

    // onClick to add Pictures to the RecyclerView
    fun onClickAddSecondaryPictureButton(){ updatePictureActivityType(2) }

    // onClick to SAVE
    fun onClickSaveEstateButton(){ verifyIfEstateCanBeSaved() }

    // Insert/Update Estate into DB
    fun insertEstateAndPicturesIntoDatabase() =
        viewModelScope.launch {
            // Insert or Update Estate into Room
            when (estate.value?.id) {
                // Null ID means Estate is not in the Database yet, CREATE
                null, 0L -> {
                    // Attach today's date to the Estate
                    updateEstateField(EstateField.Date(Calendar.getInstance().timeInMillis))
                    // Turn nearby places into a String
                    updateEstateField(EstateField.NearbyPlaces(nearbyPlaces.value?.toConcatenatedString()))
                    val insertedId: Long? = estate.value.let { estate -> estate?.let{ estateRepository.insertEstate(estate) } }
                    // Attach Estate ID to all pictures
                    secondaryPictures.value?.forEach { it.estate_id = insertedId }
                    // Insert every Picture into the Database
                    secondaryPictures.value?.forEach { pictureRepository.insertPicture(it) }
                    // Update InsertedId to trigger a notification (insertion was complete)
                    insertedId?.let { updateNotificationId(insertedId) }
                }
                // There's an ID, UPDATE
                else -> {
                    estate.value.let { estate -> estate?.let{ estateRepository.updateEstate(estate) } }
                    secondaryPictures.value?.forEach { pictureRepository.insertPicture(it) }
                }
            }
            // After all is done, clear the fields to reset the creation process
            clearEstateFields()
            // Navigate to the Estates page
            updateShouldNavigateToEstatesFragment(true)
        }

    // -----------
    // SAVE CHECK
    // -----------

    val saveOrUpdateEstate: SharedFlow<Boolean> get() = _saveOrUpdateEstate
    private val _saveOrUpdateEstate = MutableSharedFlow<Boolean>(replay = 0)
    private fun updateSaveOrUpdateEstate(boolean: Boolean) {
        viewModelScope.launch {
            _saveOrUpdateEstate.emit(boolean)
        }
    }

    val displayMissingElementsErrorDialog: SharedFlow<Boolean> get() = _displayMissingElementsErrorDialog
    private val _displayMissingElementsErrorDialog = MutableSharedFlow<Boolean>(replay = 0)
    private fun updateDisplayedMissingElementsErrorDialog(boolean: Boolean) {
        viewModelScope.launch {
            _displayMissingElementsErrorDialog.emit(boolean)
        }
    }

    // Missing elements to fill
    val missingElementsAsStrings: LiveData<String?> get() = _missingElementsAsStrings
    private val _missingElementsAsStrings = MutableLiveData<String?>(null)
    private fun updateMissingElementsAsString(elements: String?){
        _missingElementsAsStrings.value = elements
    }

    private fun verifyIfEstateCanBeSaved(){
        if (
            estate.value?.agent_id == null ||
            estate.value?.type == null ||
            estate.value?.price == null ||
            estate.value?.surface == null ||
            estate.value?.rooms == null ||
            estate.value?.bedrooms == null ||
            estate.value?.bathrooms == null ||
            estate.value?.energyScore == null ||
            estate.value?.mainPicturePath == null ||
            estate.value?.description == null ||
            estate.value?.street == null ||
            estate.value?.zipCode == null ||
            estate.value?.location == null ||
            estate.value?.latitude == null ||
            estate.value?.longitude == null
        ){
            val nullElements = mutableListOf<String?>()
            when (estate.value?.agent_id) { null -> nullElements.add("Agent") }
            when (estate.value?.type) { null -> nullElements.add("Type") }
            when (estate.value?.price) { null -> nullElements.add("Price") }
            when (estate.value?.surface) { null -> nullElements.add("Surface") }
            when (estate.value?.rooms) { null -> nullElements.add("Rooms") }
            when (estate.value?.bedrooms) { null -> nullElements.add("Bedrooms") }
            when (estate.value?.bathrooms) { null -> nullElements.add("Bathrooms") }
            when (estate.value?.energyScore) { null -> nullElements.add("Energy Score") }
            when (estate.value?.mainPicturePath) { null -> nullElements.add("Main Picture") }
            when (estate.value?.description) { null -> nullElements.add("Description") }
            when (estate.value?.street) { null -> nullElements.add("Street") }
            when (estate.value?.zipCode) { null -> nullElements.add("ZipCode") }
            when (estate.value?.location) { null -> nullElements.add("Location") }
            when (estate.value?.latitude) { null -> nullElements.add("Latitude") }
            when (estate.value?.longitude) { null -> nullElements.add("Longitude") }
            // Doesn't pass checks = can't be saved/updated
            updateMissingElementsAsString(nullElements.toString())
            updateDisplayedMissingElementsErrorDialog(true)
            updateSaveOrUpdateEstate(false)
        }else{
            // Pass checks, can be saved
            updateMissingElementsAsString(null)
            updateDisplayedMissingElementsErrorDialog(false)
            updateSaveOrUpdateEstate(true)
        }
    }

    // -----
    // CLEAR
    // -----

    // Reset all necessary values to null
    private fun clearEstateFields(){
        updateEstate(null)
        updateSecondaryPictures(null)
        updateNearbyPlaces(null)
        updateSaveOrUpdateEstate(false)
    }

    // -----
    // DEBUG
    // -----
    fun fillSecondaryPictures(){
        val randomAmountOfPictures = (1..SECONDARY_PICTURES_AMOUNT_LIMIT).random()
        val randomSecondaryPictures = mutableListOf<Picture>()
        repeat(randomAmountOfPictures) {
            randomSecondaryPictures.add(generateOneRandomPicture())
        }
        updateSecondaryPictures(randomSecondaryPictures)
    }
    fun fillEstateFields(estate: Estate){
        with(estate){
            updateEstateField(EstateField.AgentId(agent_id))
            updateEstateField(EstateField.Type(type))
            updateEstateField(EstateField.Price(price))
            updateEstateField(EstateField.Surface(surface))
            updateEstateField(EstateField.Rooms(rooms))
            updateEstateField(EstateField.Bedrooms(bedrooms))
            updateEstateField(EstateField.Bathrooms(bathrooms))
            updateEstateField(EstateField.EnergyScore(energyScore))
            updateEstateField(EstateField.EnergyRating(energyRating))
            updateEstateField(EstateField.MainPicturePath(mainPicturePath))
            updateEstateField(EstateField.Description(description))
            updateEstateField(EstateField.HighSpeedInternet(highSpeedInternet))
            updateEstateField(EstateField.Furnished(furnished))
            updateEstateField(EstateField.DisabledAccessibility(disabledAccessibility))
            updateEstateField(EstateField.Garden(garden))
            updateEstateField(EstateField.Street(street))
            updateEstateField(EstateField.ZipCode(zipCode))
            updateEstateField(EstateField.Latitude(latitude))
            updateEstateField(EstateField.Longitude(longitude))
            updateEstateField(EstateField.Location(location))
            updateEstateField(EstateField.NearbyPlaces(nearbyPlaces))
        }
        updateSecondaryPictures(listOf(Picture("", "Room", 0, 0)))
    }
}