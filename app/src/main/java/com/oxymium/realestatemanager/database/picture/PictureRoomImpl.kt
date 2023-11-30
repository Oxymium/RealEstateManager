package com.oxymium.realestatemanager.database.picture

import com.oxymium.realestatemanager.model.databaseitems.Picture
import kotlinx.coroutines.flow.Flow

class PictureRoomImpl(private val pictureDao: PictureDao): PictureRepository {
    override fun getAllPicturesForGivenEstateId(estateId: Long): Flow<List<Picture>> {
        return pictureDao.getAllPicturesForGivenEstateId(estateId)
    }

    override suspend fun insertPicture(picture: Picture) {
        pictureDao.insertPicture(picture)
    }
}