package com.oxymium.realestatemanager.database

import com.oxymium.realestatemanager.model.Estate

// --------------------
// EstateDataRepository
// --------------------

class EstateDataRepository(private val estateDao: EstateDao) {

    // CREATE
    fun createEstate(estate: Estate): Long {
        return estateDao.createEstate(estate)
    }

}