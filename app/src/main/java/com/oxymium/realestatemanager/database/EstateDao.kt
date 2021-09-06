package com.oxymium.realestatemanager.database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import com.oxymium.realestatemanager.model.Estate
import kotlinx.coroutines.flow.Flow

// ---------
// EstateDao
// ---------

@Dao
interface EstateDao {

    @Query("SELECT * FROM estate ORDER BY location ASC")
    fun getLocalisedEstate(): Flow<List<Estate>>

    @RawQuery(observedEntities = [Estate::class])
    fun getSearchedEstates(search: SimpleSQLiteQuery?): Flow<List<Estate>>

    @Query("SELECT * FROM estate WHERE price >= :minPrice AND price <= :maxPrice")
    fun getSearchedEstatesTest2(minPrice: Int, maxPrice: Int): Flow<List<Estate>>

    // CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(estate: Estate): Long

    // DELETE
    @Query("DELETE FROM estate")
    suspend fun deleteAll()

    // UPDATE
    @Update
    suspend fun update(estate: Estate)

    // Expose DATABASE to ContentProvider
    @Query("SELECT * FROM Estate WHERE id = :id")
    fun getEstateWithCursor(id: Long): Cursor?

}