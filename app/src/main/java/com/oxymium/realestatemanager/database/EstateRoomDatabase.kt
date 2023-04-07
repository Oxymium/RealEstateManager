package com.oxymium.realestatemanager.database
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.oxymium.realestatemanager.model.Agent
import com.oxymium.realestatemanager.model.Estate
import com.oxymium.realestatemanager.model.Picture
import com.oxymium.realestatemanager.provideRandomAgents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// -------------------------
// EstateRoomDatabase (ROOM)
// -------------------------

@Database(entities = [Estate::class, Picture::class, Agent::class], version = 2)
abstract class EstateRoomDatabase : RoomDatabase() {

    abstract fun estateDao(): EstateDao
    abstract fun pictureDao(): PictureDao
    abstract fun agentDao(): AgentDao

    companion object {
        @Volatile
        private var INSTANCE: EstateRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): EstateRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EstateRoomDatabase::class.java,
                    "estate_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(EstateDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        // --- INSTANCE ---
        fun getInstance(context: Context): EstateRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(EstateRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            EstateRoomDatabase::class.java, "MyDatabase.db"
                        )
                            .build()
                    }
                }
            }
            return INSTANCE
        }

        private class EstateDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onCreate method to populate the database.
             */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.estateDao())
                        populateDatabase(database.agentDao())
                    }
                }
            }
        }

        /**
         * Populate the database in a new coroutine.
         * If you want to start with more words, just add them.
         */
        suspend fun populateDatabase(estateDao: EstateDao) {
        }

        suspend fun populateDatabase(pictureDao: PictureDao) {}

        suspend fun populateDatabase(agentDao: AgentDao){
            for (agent in provideRandomAgents(15)) agentDao.insert(agent)
        }
    }
}