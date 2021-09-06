package com.oxymium.realestatemanager.database

import androidx.annotation.WorkerThread
import com.oxymium.realestatemanager.model.Estate
import com.oxymium.realestatemanager.model.Picture
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PictureRepository(private val pictureDao: PictureDao) {

    // Get
    //val allPicturesForGivenEstateId: Flow<List<Picture>> = pictureDao.getAllPicturesForGivenEstateId()

    // Query in DB with string parameter
    fun getAllPicturesForGivenEstateId(estateId: Long): Flow<List<Picture>> {
        return pictureDao.getAllPicturesForGivenEstateId(estateId)
    }

    // Insert Picture into DB
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(picture: Picture) {
        pictureDao.insert(picture)
    }

    // Testing
    fun provideTestPictures(): Flow<List<Picture>> = flow{
        val listTest: List<Picture> = mutableListOf(
            Picture("android.resource://com.oxymium.realestatemanager/drawable/estate_placeholder2", "test"),
            Picture("android.resource://com.oxymium.realestatemanager/drawable/estate_placeholder3", "test2"),
            Picture("android.resource://com.oxymium.realestatemanager/drawable/estate_placeholder4", "test3"),
            Picture("android.resource://com.oxymium.realestatemanager/drawable/estate_placeholder5", "test4"),
            Picture("android.resource://com.oxymium.realestatemanager/drawable/estate_placeholder6", "test5"),
            Picture("android.resource://com.oxymium.realestatemanager/drawable/estate_placeholder7", "test6"),
            Picture("android.resource://com.oxymium.realestatemanager/drawable/estate_placeholder8", "test7"),
            Picture("android.resource://com.oxymium.realestatemanager/drawable/estate_placeholder9", "test8")
        )
            emit(listTest)
        }
}