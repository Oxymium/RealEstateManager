package com.oxymium.realestatemanager.database

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

// ------------------
// EstatesApplication
// ------------------

class EstatesApplication : Application() {

    // No need to cancel this scope as it'll be torn down with the process
        val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
        val database by lazy { EstateRoomDatabase.getDatabase(this, applicationScope) }
        val repository by lazy { EstateRepository(database.estateDao()) }
        val repository2 by lazy { PictureRepository(database.pictureDao()) }
        val repository3 by lazy { AgentRepository(database.agentDao()) }
}