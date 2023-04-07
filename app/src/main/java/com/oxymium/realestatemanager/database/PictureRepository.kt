package com.oxymium.realestatemanager.database

import com.oxymium.realestatemanager.model.Picture
import kotlinx.coroutines.flow.Flow

// -----------------
// PictureRepository
// -----------------

class PictureRepository(private val pictureDao: PictureDao) {

    // Query in DB with Long parameter
    fun getAllPicturesForGivenEstateId(estateId: Long): Flow<List<Picture>> {
        return pictureDao.getAllPicturesForGivenEstateId(estateId)
    }

    // Insert Picture into DB
    suspend fun insert(picture: Picture) {
        pictureDao.insert(picture)
    }

}