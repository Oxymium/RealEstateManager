package com.oxymium.realestatemanager.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.oxymium.realestatemanager.model.Picture
import kotlinx.coroutines.flow.Flow

// ----------
// PictureDao
// ----------

@Dao
interface PictureDao {

    // CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(picture: Picture)

    // DELETE
    @Query("DELETE FROM picture")
    suspend fun deleteAll()

    // QUERY
    @Query("SELECT * FROM picture WHERE estate_id = :estateId")
    fun getAllPicturesForGivenEstateId(estateId: Long?): Flow<List<Picture>>

}