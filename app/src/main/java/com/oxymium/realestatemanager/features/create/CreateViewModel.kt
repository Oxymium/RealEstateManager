package com.oxymium.realestatemanager.features.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.oxymium.realestatemanager.CREATE_STEPS
import com.oxymium.realestatemanager.ESTATE_TYPES
import com.oxymium.realestatemanager.NEARBY_PLACES
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.SECONDARY_PICTURES_AMOUNT_LIMIT
import com.oxymium.realestatemanager.database.agent.AgentRepository
import com.oxymium.realestatemanager.database.estate.EstateRepository
import com.oxymium.realestatemanager.database.picture.PictureRepository
import com.oxymium.realestatemanager.model.EstateField
import com.oxymium.realestatemanager.model.Label
import com.oxymium.realestatemanager.model.ReachedSide
import com.oxymium.realestatemanager.model.Step
import com.oxymium.realestatemanager.model.databaseitems.Agent
import com.oxymium.realestatemanager.model.databaseitems.Estate
import com.oxymium.realestatemanager.model.databaseitems.Picture
import kotlinx.coroutines.launch
import java.util.Calendar

// ---------------
// CreateViewModel
// ---------------
class CreateViewModel(agentRepository: AgentRepository, private val estateRepository: EstateRepository, private val pictureRepository: PictureRepository): ViewModel() {

    // ------
    // ESTATE
    // ------

    // CREATE
    val estate: LiveData<Estate?> get() = _estate
    private val _estate = MutableLiveData(Estate())
    fun updateEstate(estate: Estate?){
        _estate.value = estate
    }

    // EDIT
    val editedEstate: LiveData<Estate?> get() = _editedEstate
    private val _editedEstate = MutableLiveData<Estate?>(null)
    fun updateEditedEstate(estate: Estate?){
        _editedEstate.value = estate
    }

    // UPDATE ESTATE FIELDS
    fun updateEstateField(field: EstateField){
        val currentEstate = _estate.value ?: Estate()
        when (field){
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
    val selectedStep: LiveData<Int?> get() = _selectedStep
    private val _selectedStep: MutableLiveData<Int?> = MutableLiveData<Int?>(null)
    fun updateSelectedStep(value: Int?){
        _selectedStep.value = when (value){
            null -> R.id.stepAgentFragment
            0 -> R.id.stepOverviewFragment
            1 -> R.id.stepAgentFragment
            2 -> R.id.stepTypeFragment
            3 -> R.id.stepValuesEnergyScoreFragment
            4 -> R.id.stepMainPictureFragment
            5 -> R.id.stepSecondaryPicturesFragment
            6 -> R.id.stepMiscFragment
            7 -> R.id.stepAddressFragment
            8 -> R.id.stepNearbyPlacesFragment
            else -> R.id.stepOverviewFragment
        }
    }

    val currentStep: LiveData<Int> get() = _currentStep
    private val _currentStep = MutableLiveData<Int>()
    fun updateCurrentStep(value: Int){
        _currentStep.value = value
    }

    val reachedStepSide: LiveData<ReachedSide> get() = _reachedSide
    // Initial value LEFT (recyclerView starts on first element)
    private val _reachedSide = MutableLiveData<ReachedSide>(ReachedSide.LeftSide)
    fun updateReachedStepSide(reachedSide: ReachedSide){
        _reachedSide.value = reachedSide
    }

    val reachedAgentSide: LiveData<ReachedSide> get() = _reachedAgentSide
    private val _reachedAgentSide = MutableLiveData<ReachedSide>(ReachedSide.TopSide)
    fun updateReachedAgentSide(reachedSide: ReachedSide){
        _reachedAgentSide.value = reachedSide
    }

    val reachedTypeSide: LiveData<ReachedSide> get() = _reachedTypeSide
    private val _reachedTypeSide = MutableLiveData<ReachedSide>(ReachedSide.TopSide)
    fun updateReachedTypeSide(reachedSide: ReachedSide){
        _reachedTypeSide.value = reachedSide
    }

    val reachedNearbyPlacesSide: LiveData<ReachedSide> get() = _reachedNearbyPlacesSide
    private val _reachedNearbyPlacesSide= MutableLiveData<ReachedSide>(ReachedSide.TopSide)
    fun updateReachedNearbyPlacesSide(reachedSide: ReachedSide){
        _reachedNearbyPlacesSide.value = reachedSide
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

    // -----
    // STEPS
    // -----
    val createSteps: LiveData<List<Step>?> get() = _createSteps
    private val _createSteps = MutableLiveData(CREATE_STEPS)
    fun updateCreateStep(steps: List<Step>?){
        _createSteps.value = steps
    }

    // ------
    // STEP 1
    // ------

    // AGENTS
    val agentsFromDatabase = agentRepository.getAllAgents().asLiveData()
    val agents: LiveData<List<Agent>?> get() = _agents
    private val _agents = MutableLiveData<List<Agent>?>()
    fun updateAgents(agents: List<Agent>?) {
        _agents.value = agents
    }

    // ------
    // STEP 2
    // ------

    // TYPES
    val types: LiveData<List<Label>> get() = _types
    private val _types = MutableLiveData(ESTATE_TYPES)
    fun updateTypes(types: List<Label>?) {
        _types.value = types
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

    // -------------------------------------------
    // STEP 6 - ADDRESS, LAT & LNG & NEARBY PLACES
    // -------------------------------------------

    // REVERSE GEOCODING (GET LAT & LNG FROM ADDRESS)
    val enableReverseGeoCoding: LiveData<Boolean?> get() = _enableReverseGeoCoding
    private val _enableReverseGeoCoding = MutableLiveData<Boolean?>()
    fun updateEnableReverseGeoCoding(boolean: Boolean){
        _enableReverseGeoCoding.value = boolean
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
        val nearbyPlaces = selectedNearbyPlaces.value?.toMutableList()
        if (nearbyPlaces?.contains(label) == true) nearbyPlaces.remove(label)
        else nearbyPlaces?.add(label)
        _selectedNearbyPlaces.value = nearbyPlaces?.toList()
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

    fun onClickDeleteMainPictureButton(){ updateEstateField(EstateField.MainPicturePath(null) ) }

    // onClick to add Pictures to the RecyclerView
    fun onClickAddSecondaryPictureButton(){ updatePictureActivityType(2) }

    // onClick to SAVE
    fun onClickSaveEstateButton(){ verifyIfEstateCanBeSaved() }

    // Insert Estate into DB
    fun insertEstateAndPicturesIntoDatabase() =
        viewModelScope.launch {
            // Attach today's date to the Estate
            updateEstateField(EstateField.Date(Calendar.getInstance().timeInMillis))
            // Insert Estate into Room
            val insertedId: Long? = estate.value?.let { estateRepository.insert(it) }
            // Attach Estate ID to all pictures
            secondaryPictures.value?.forEach { it.estate_id = insertedId }
            // Insert every Picture into the Database
            secondaryPictures.value?.forEach { pictureRepository.insert(it) }
            // Update InsertedId to trigger a notification (insertion was complete)
            if (insertedId != null) updateNotificationId(insertedId)
            // After all is done, clear the fields to reset the creation process
            clearEstateFields()
            // Notify Estate is done being created
            toggleIsEstateDoneBeingCreatedOrEdited(true)
        }

    // Update Estate into DB
    fun updateEstateIntoDatabase() =
        viewModelScope.launch {
            // Update Edited Estate into Room
            editedEstate.value?.let{ estateRepository.updateEstate(it) }
            // Clear fields
            clearEstateFields()
            // Notify Estate is done being created
            toggleIsEstateDoneBeingCreatedOrEdited(true)
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

            updateMissingElementsAsString(nullElements.toString())
        }else{
            updateMissingElementsAsString(null)
        }
    }

    // -----
    // CLEAR
    // -----

    // Reset all values to null
    private fun clearEstateFields(){
        updateEstate(null)
    }

    // -----
    // DEBUG
    // -----

    // TODO replace with a more generic random method
    fun fillSecondaryPictures(){
        val randomAmountOfPictures = (1..SECONDARY_PICTURES_AMOUNT_LIMIT).random()
        val secondaryPictures = mutableListOf<Picture>()
        val comments = listOf("Room", "Kitchen", "Garden", "Room 2", "Room 3", "Room 4", "Bathroom", "Living Room")
        for (picture in 1.. randomAmountOfPictures){
            secondaryPictures.add(
                Picture(
                "",
                comments.random()
            )
            )
        }
        updateSecondaryPictures(secondaryPictures)
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
            updateEstateField(EstateField.Location(location))
            updateEstateField(EstateField.NearbyPlaces(nearbyPlaces))
        }
        updateSecondaryPictures(listOf(Picture("", "Room", 0, 0)))
    }
}