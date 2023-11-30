package com.oxymium.realestatemanager.database.estate

import androidx.sqlite.db.SimpleSQLiteQuery
import com.oxymium.realestatemanager.model.databaseitems.Estate
import kotlinx.coroutines.flow.Flow

interface EstateRepository {

    // GET
    fun getAllEstates(): Flow<List<Estate>>
    fun queryEstates(query: SimpleSQLiteQuery): Flow<List<Estate>>

    fun getEstateWithId(estateId: Long): Flow<Estate>

    // INSERT
    suspend fun insertEstate(estate: Estate): Long

    // UPDATE
    suspend fun updateEstate(estate: Estate)

    // Delete (mainly for testing purposes)
    suspend fun deleteAllEstates()

}