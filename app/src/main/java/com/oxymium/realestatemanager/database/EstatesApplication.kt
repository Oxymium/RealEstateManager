package com.oxymium.realestatemanager.database

import android.app.Application
import com.oxymium.realestatemanager.database.agent.AgentRepository
import com.oxymium.realestatemanager.database.estate.EstateRepository
import com.oxymium.realestatemanager.database.picture.PictureRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

// ------------------
// EstatesApplication
// ------------------

class EstatesApplication : Application() {

    // No need to cancel this scope as it'll be torn down with the process
    private val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
        val database by lazy { AppRoomDatabase.getDatabase(this, applicationScope) }
        val estateRepository by lazy { EstateRepository(database.estateDao()) }
        val pictureRepository by lazy { PictureRepository(database.pictureDao()) }
        val agentRepository by lazy { AgentRepository(database.agentDao()) }
}