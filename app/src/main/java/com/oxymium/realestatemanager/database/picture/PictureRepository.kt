package com.oxymium.realestatemanager.database.picture

import com.oxymium.realestatemanager.model.databaseitems.Picture
import kotlinx.coroutines.flow.Flow

interface PictureRepository {

    fun getAllPicturesForGivenEstateId(estateId: Long): Flow<List<Picture>>

    suspend fun insertPicture(picture: Picture)

}