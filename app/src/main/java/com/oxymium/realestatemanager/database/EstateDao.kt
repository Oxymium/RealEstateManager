package com.oxymium.realestatemanager.database

import androidx.room.*
import com.oxymium.realestatemanager.model.Estate

// ---------
// EstateDao
// ---------

@Dao
interface EstateDao {

    // CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createEstate(estate: Estate): Long

    // QUERY
    @Query("SELECT * FROM estates")
    fun getAllEstates(): List<Estate>

    // DELETE (testing purposes)
    @Delete
    fun delete(estate: Estate)

}