package com.oxymium.realestatemanager

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.oxymium.realestatemanager.database.EstateContentProvider
import com.oxymium.realestatemanager.database.EstateRoomDatabase
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class ItemContentProviderTest {
    // FOR DATA
    private var mContentResolver: ContentResolver? = null
    @Before
    fun setUp() {
        Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
        EstateRoomDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        mContentResolver = InstrumentationRegistry.getInstrumentation().targetContext.contentResolver}

    @Test
    fun getItemsWhenNoItemInserted() {
        val cursor = mContentResolver!!.query(ContentUris.withAppendedId(EstateContentProvider.URI_ITEM, USER_ID), null, null, null, null)
        assertThat(cursor, notNullValue())
        assertThat(cursor!!.count, `is`(0))
        cursor.close()}

    @Test
    fun insertAndGetItem() {
        // BEFORE : Adding demo item
        val userUri = mContentResolver!!.insert(EstateContentProvider.URI_ITEM, generateItem())
        // TEST
        val cursor = mContentResolver!!.query(ContentUris.withAppendedId(EstateContentProvider.URI_ITEM, USER_ID), null, null, null, null)
        assertThat(cursor, notNullValue())
        assertThat(cursor!!.count, `is`(1))
        assertThat(cursor.moveToFirst(), `is`(true))
        assertThat(cursor.getLong(cursor.getColumnIndexOrThrow("addedDate")), `is`(100000L))
        // CLEAR
       mContentResolver!!.delete(ContentUris.withAppendedId(EstateContentProvider.URI_ITEM, USER_ID), null)
    }

    private fun generateItem(): ContentValues {
        val values = ContentValues()
        values.put("addedDate", 100000L)
        values.put("wasSold", true)
        values.put("soldDate", 100000L)
        values.put("type", "Farm")
        values.put("price", 1000000)
        values.put("energyScore", "A")
        values.put("surface", 100)
        values.put("rooms", 5)
        values.put("bedrooms", 2)
        values.put("bathrooms", 1)
        values.put("address", "22 rue Farm")
        values.put("zipCode", 75000)
        values.put("location", "Paris")
        values.put("highSpeedInternet", true)
        values.put("nearbyPlaces", "Restaurant School")
        values.put("description", "Cosy place")
        values.put("mainPicturePath", "")
        values.put("agent", "Agent X")
        values.put("id", 15L)
        return values}

    companion object {
        private val USER_ID: Long = 15L
    }
}