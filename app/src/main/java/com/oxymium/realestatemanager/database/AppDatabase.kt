package com.oxymium.realestatemanager.database

import android.content.ContentValues
import android.content.Context
import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.OnConflictStrategy
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.oxymium.realestatemanager.model.Estate

// ------------
// RoomDatabase
// ------------

@Database(entities = [Estate::class], version = 1, exportSchema = false)

//@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    // --- DAO ---
    abstract fun estateDao(): EstateDao

    companion object {

        // --- SINGLETON ---
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // --- INSTANCE ---
        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java, "Database.db"
                        )
                            .addCallback(prepopulateDatabase())
                            .build()
                    }
                }
            }
            return INSTANCE
        }

        // PROVIDE FIRST ENTRIES
        private fun prepopulateDatabase(): Callback {
            return object : Callback() {
                override fun onCreate(@NonNull db: SupportSQLiteDatabase) {
                    super.onCreate(db)

                }

            }

        }

    }

}