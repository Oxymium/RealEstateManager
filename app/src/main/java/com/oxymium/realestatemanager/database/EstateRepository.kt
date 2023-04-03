package com.oxymium.realestatemanager.database

import androidx.annotation.WorkerThread
import androidx.sqlite.db.SimpleSQLiteQuery
import com.oxymium.realestatemanager.model.Estate
import kotlinx.coroutines.flow.Flow

// ----------------
// EstateRepository
// ----------------

class EstateRepository(private val estateDao: EstateDao) {

    // Get allEstates from the DB as Flow
    val allEstates: Flow<List<Estate>> = estateDao.getLocalisedEstate()

    fun getSearchedEstates(search: SimpleSQLiteQuery): Flow<List<Estate>> {
        return estateDao.getSearchedEstates(search)
    }

    fun getEstateWithId(id: Long): Flow<Estate> {
        return estateDao.getEstateWithId(id)
    }

    // Insert Estate into DB
    @WorkerThread
    suspend fun insert(estate: Estate): Long {
        return estateDao.insert(estate)
    }

    // Delete Estate from DB (mainly for testing purposes)
    @WorkerThread
    suspend fun deleteAll() {
        estateDao.deleteAll()
    }

    // Update Estate in DB
    @WorkerThread
    suspend fun updateEstate(estate: Estate) {
        estateDao.update(estate)
    }

}