package com.oxymium.realestatemanager.database

import androidx.room.*
import com.oxymium.realestatemanager.model.Estate
import kotlinx.coroutines.flow.Flow

// ---------
// EstateDao
// ---------

@Dao
interface EstateDao {

    @Query("SELECT * FROM estate ORDER BY location ASC")
    fun getLocalisedEstate(): Flow<List<Estate>>

    @Query("SELECT * FROM estate WHERE location LIKE '%' || :search || '%'")
    fun getSearchedEstates(search: String?): Flow<List<Estate>>

    // CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(estate: Estate)

    // DELETE
    @Query("DELETE FROM estate")
    suspend fun deleteAll()

}