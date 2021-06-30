package com.oxymium.realestatemanager.database

import androidx.annotation.WorkerThread
import com.oxymium.realestatemanager.model.Estate
import kotlinx.coroutines.flow.Flow

// ----------------
// EstateRepository
// ----------------

class EstateRepository(private val estateDao: EstateDao) {


    // Get allEstates from the DB as Flow
    val allEstates: Flow<List<Estate>> = estateDao.getLocalisedEstate()

    // Query in DB with string parameter
    fun getSearchedEstate(search: String): Flow<List<Estate>> {
        return estateDao.getSearchedEstates(search)
    }

    // Insert Estate into DB
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(estate: Estate) {
        estateDao.insert(estate)
    }

    // Delete Estate from DB (mainly for testing purposes)
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll() {
        estateDao.deleteAll()
    }
}