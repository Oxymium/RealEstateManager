package com.oxymium.realestatemanager.database.estate

import androidx.sqlite.db.SimpleSQLiteQuery
import com.oxymium.realestatemanager.model.databaseitems.Estate
import kotlinx.coroutines.flow.Flow

class EstateRoomImpl(private val estateDao: EstateDao): EstateRepository {
    override fun getAllEstates(): Flow<List<Estate>> =
        estateDao.getLocalisedEstate()

    override fun queryEstates(query: SimpleSQLiteQuery): Flow<List<Estate>> {
        return estateDao.queryEstates(query)
    }

    override fun getEstateWithId(estateId: Long): Flow<Estate> {
        return estateDao.getEstateWithId(estateId)
    }

    override suspend fun insertEstate(estate: Estate): Long {
        return estateDao.insert(estate)
    }

    override suspend fun updateEstate(estate: Estate) {
        estateDao.update(estate)
    }

    override suspend fun deleteAllEstates() {
        estateDao.deleteAll()
    }
}