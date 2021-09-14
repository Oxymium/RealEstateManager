package com.oxymium.realestatemanager.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.*
import com.oxymium.realestatemanager.database.EstateRepository
import com.oxymium.realestatemanager.database.PictureRepository
import com.oxymium.realestatemanager.misc.Utils
import com.oxymium.realestatemanager.model.Estate
import com.oxymium.realestatemanager.model.Picture
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

// ---------------
// CreateViewModel
// ---------------

class CreateViewModel(private val estateRepository: EstateRepository, private val pictureRepository: PictureRepository): ViewModel() {

    // Define edit or create route 1 = CREATE / 2 = EDIT
    var createOrEditMode: MutableLiveData<Int> = MutableLiveData(1)
    val editedEstate: MutableLiveData<Estate> = MutableLiveData()
    // Estate values
    var editedId: MutableLiveData<Long> = MutableLiveData(0L)
    var addedDate: MutableLiveData<Long> = MutableLiveData(0L)
    var wasSold: MutableLiveData<Boolean> = MutableLiveData(false)
    var soldDate: MutableLiveData<Long> = MutableLiveData(0L)
    var type: MutableLiveData<String> = MutableLiveData("Select type")
    var price: MutableLiveData<Int> = MutableLiveData(0)
    var energyScore: MutableLiveData<String> = MutableLiveData("Select energy score")
    var surface: MutableLiveData<Int> = MutableLiveData(0)
    var rooms: MutableLiveData<Int> = MutableLiveData(0)
    var bedrooms: MutableLiveData<Int> = MutableLiveData(0)
    var bathrooms: MutableLiveData<Int> = MutableLiveData(0)
    var address: MutableLiveData<String> = MutableLiveData("")
    var zipCode: MutableLiveData<Int> = MutableLiveData(0)
    var location: MutableLiveData<String> = MutableLiveData("")
    var highSpeedInternet: MutableLiveData<Boolean> = MutableLiveData(false)
    var description: MutableLiveData<String> = MutableLiveData("")
    var nearbyPlaces: MutableLiveData<String> = MutableLiveData("")
    var mainPicturePath: MutableLiveData<String> = MutableLiveData()
    var agent: MutableLiveData<String> = MutableLiveData("Select agent name")

    // Secondary Pictures
    var listSecondaryPictures: MutableLiveData<MutableList<Picture>> = MutableLiveData()
    var triggerPictureActivityFrom: MutableLiveData<Int> = MutableLiveData(0)
    var triggerSaveAlertDialog: MutableLiveData<Boolean> = MutableLiveData(false)
    var navigateToEstatesFragment: MutableLiveData<Boolean> = MutableLiveData(false)
    // Notifications trigger
    var triggerNotification: MutableLiveData<Boolean> = MutableLiveData(false)
    var returnedEstateIdValue: MutableLiveData<Long> = MutableLiveData(-1)


    var wasEstateTypeClicked: MutableLiveData<Boolean> = MutableLiveData(false)
    fun onClickEstateType(){
        wasEstateTypeClicked.postValue(true)
    }

    var wasEstateEnergyScoreClicked: MutableLiveData<Boolean> = MutableLiveData(false)
    fun onClickEnergyScore(){
        wasEstateEnergyScoreClicked.postValue(true)
    }

    var wasAgentNameClicked: MutableLiveData<Boolean> = MutableLiveData(false)
    fun onClickAgentName(){
        wasAgentNameClicked.postValue(true)
    }

    fun <T> MutableLiveData<MutableList<T>>.addNewPicture(item: T) {
        val oldValue = this.value ?: mutableListOf()
        oldValue.add(item)
        this.value = oldValue
    }

    fun <T> MutableLiveData<MutableList<T>>.deletePicture(item: T) {
        val oldValue = this.value ?: mutableListOf()
        oldValue.remove(item)
        this.value = oldValue
    }

    fun addPictureToSecondaryList(picture: Picture){
       listSecondaryPictures.addNewPicture(picture)
    }

    fun deletePictureFromSecondaryList(picture: Picture){
        listSecondaryPictures.deletePicture(picture)
    }

    fun updatePictureCommentFromSecondaryList(picture: Picture){
        listSecondaryPictures.value?.find{
            it.path == picture.path}?.comment = picture.comment
        }

    private fun updatePictureEstateIdFromSecondaryList(picture: Picture){
        listSecondaryPictures.value?.find{
            it.path == picture.path}?.estate_id = picture.estate_id
    }

    // onClick to add Main Picture
    fun onClickAddMainPictureButton(){
        Log.d(TAG, "onClickAddMainPictureButton: called")
        triggerPictureActivityFrom.postValue(1)
    }

    fun onClickDeleteMainPictureButton(){
        Log.d(TAG, "onClickDeleteMainPictureButton: called")
        mainPicturePath.postValue("")
    }

    // onClick to add Pictures to the RecyclerView
    fun onClickAddSecondaryPictureButton(){
        Log.d(TAG, "onClickAddSecondaryPicturesButton: called")
        triggerPictureActivityFrom.postValue(2)
    }

    // onClick to SAVE
    fun onClickSaveEstateButton(){
        Log.d(TAG, "onClickSaveEstateButton: called")
        triggerSaveAlertDialog.postValue(true)
    }

    // Insert Estate into DB
    fun insertEstateIntoDatabase() = viewModelScope.launch {

        val insertedId: Long = estateRepository.insert(
            Estate(
                addedDate.value ?: 0L,
                wasSold.value ?: false,
                soldDate.value ?: 0L,
                type.value.toString(),
                price.value ?: 0,
                energyScore.value.toString(),
                surface.value ?: 0,
                rooms.value ?: 0,
                bedrooms.value ?: 0,
                bathrooms.value ?: 0,
                address.value.toString(),
                zipCode.value ?: 0,
                location.value.toString(),
                highSpeedInternet.value ?: false,
                nearbyPlaces.value.toString(),
                description.value.toString(),
                mainPicturePath.value.toString(),
                agent.value.toString()
            ))
        Log.i("INSERT_ID", "Estate Inserted ID is: $insertedId")

        triggerNotification.postValue(true)
        returnedEstateIdValue.postValue(insertedId)

        // Attach returned Estate ID to all Pictures
        if (!listSecondaryPictures.value.isNullOrEmpty()) {

            for (i in 0 until listSecondaryPictures.value!!.size) {
                listSecondaryPictures.value!![i].estate_id = insertedId
            }
            // Insert Pictures into DB
            for (i in 0 until listSecondaryPictures.value!!.size) {
                pictureRepository.insert(listSecondaryPictures.value!![i])
            }
        }

        navigateToEstatesFragment.postValue(true)

    }

    // Update Estate into DB
    fun updateEstateIntoDatabase() = viewModelScope.launch {

        estateRepository.updateEstate(Estate(
            addedDate.value ?: 0L,
            wasSold.value ?: false,
            soldDate.value ?: 0L,
            type.value.toString(),
            price.value ?: 0,
            energyScore.value.toString(),
            surface.value ?: 0,
            rooms.value ?: 0,
            bedrooms.value ?: 0,
            bathrooms.value ?: 0,
            address.value.toString(),
            zipCode.value ?: 0,
            location.value.toString(),
            highSpeedInternet.value ?: false,
            nearbyPlaces.value.toString(),
            description.value.toString(),
            mainPicturePath.value.toString(),
            agent.value.toString(),
            editedId.value
        ))

        createOrEditMode.value = 1

        navigateToEstatesFragment.postValue(true)

    }



    // -------
    // Testing
    // -------

    // Debug Methods (Delete & Add for testing purposes)
    // +1 Estate
    fun onClickAddOneButton(){ insertRandomEstate(estateRepository.provideRandomEstate()) }
    // +1k Estates
    fun onClickAddThousandButton(){ for (i in 1..1000) { insertRandomEstate(estateRepository.provideRandomEstate()) }}
    // +10k Estates
    fun onClickAddTenThousandButton(){ for (i in 1..10000) { insertRandomEstate(estateRepository.provideRandomEstate()) }}


    // Insert Estate in DB, returns Estate ID
    private fun insertRandomEstate(estate: Estate) = viewModelScope.launch {
        val insertedId: Long = estateRepository.insert(estate)
        Log.i("INSERT_ID", "Estate Inserted ID is: $insertedId")
        // Set LiveData values for notifications
        triggerNotification.postValue(true)
        returnedEstateIdValue.postValue(insertedId)

        pictureRepository.insert(Picture("testing", "testingdescription", insertedId))

    }

    // Delete all
    fun onClickDeleteAllButton(){ viewModelScope.launch { estateRepository.deleteAll() }}

}