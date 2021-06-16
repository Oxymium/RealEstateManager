package com.oxymium.realestatemanager.viewmodel

import android.content.Context
import com.oxymium.realestatemanager.database.AppDatabase
import com.oxymium.realestatemanager.database.EstateDataRepository

// ---------
// Injection
// ---------

class Injection {

    companion object {

        // -- DATABASE INJECTION
        fun provideEstateDataSource(context: Context?): EstateDataRepository {
            val database: AppDatabase? = AppDatabase.getInstance(context!!)
            return EstateDataRepository(database!!.estateDao())
        }

        // VIEWMODEL INJECTION

            // EstateViewModel
            fun provideEstateViewModelFactory(context: Context?): EstateViewModelFactory {
                return EstateViewModelFactory()
            }

    }

}