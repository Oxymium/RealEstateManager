package com.oxymium.realestatemanager.database.estate

import androidx.sqlite.db.SimpleSQLiteQuery
import com.oxymium.realestatemanager.model.databaseitems.Estate
import kotlinx.coroutines.flow.Flow

// ----------------
// EstateRepository
// ----------------

class EstateRepository(private val estateDao: EstateDao) {

    // Get allEstates from the DB as Flow
    fun getAllEstates(): Flow<List<Estate>> = estateDao.getLocalisedEstate()

    fun getSearchedEstates(search: SimpleSQLiteQuery): Flow<List<Estate>> {
        return estateDao.getSearchedEstates(search)
    }

    fun getEstateWithId(id: Long): Flow<Estate> {
        return estateDao.getEstateWithId(id)
    }

    // Insert Estate into DB
    suspend fun insert(estate: Estate): Long {
        return estateDao.insert(estate)
    }

    // Delete Estate from DB (mainly for testing purposes)
    suspend fun deleteAll() {
        estateDao.deleteAll()
    }

    // Update Estate in DB
    suspend fun updateEstate(estate: Estate) {
        estateDao.update(estate)
    }

}