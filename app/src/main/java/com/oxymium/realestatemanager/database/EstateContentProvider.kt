package com.oxymium.realestatemanager.database

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.oxymium.realestatemanager.model.databaseitems.Estate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

// ---------------------
// EstateContentProvider
// ---------------------

class EstateContentProvider: ContentProvider() {

    val applicationScope = CoroutineScope(SupervisorJob())

    companion object {
        // FOR DATA
        const val AUTHORITY = "com.oxymium.realestatemanager.provider"
        val TABLE_NAME: String = Estate::class.java.simpleName
        val URI_ITEM: Uri = Uri.parse("content://$AUTHORITY/$TABLE_NAME")
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        if (context != null) {
            val userId = ContentUris.parseId(uri)
            val db = AppRoomDatabase.getInstance(context!!)
            val cursor = db!!.estateDao().getEstateWithCursor(userId)
            cursor?.setNotificationUri(context!!.contentResolver, uri)
            return cursor
        }

        throw IllegalArgumentException("Failed to query row for uri $uri")}

    override fun getType(uri: Uri): String {
        return "vnd.android.cursor.item/$AUTHORITY.$TABLE_NAME"
    }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri {
        if (context != null) {
            val estate = provideEstate(contentValues!!)
            val db = AppRoomDatabase.getInstance(context!!)
            val id = provideEstate(contentValues)?.let { db!!.estateDao().insertTest(it) } ?: 0
            context!!.contentResolver.notifyChange(uri, null)
            return ContentUris.withAppendedId(uri, id)
        }
        throw IllegalArgumentException("Failed to insert row into $uri")
    }

    override fun delete(uri: Uri, s: String?, strings: Array<String>?): Int {
        /*
        if (context != null) {
            val db = EstateRoomDatabase.getInstance(context!!)
            val count = db!!.estateDao().deleteItem(ContentUris.parseId(uri))
            context.contentResolver.notifyChange(uri, null)
            return count }

        throw IllegalArgumentException("Failed to delete row into $uri")
        */
        return 0
    }

    override fun update(uri: Uri, contentValues: ContentValues?, s: String?, strings: Array<String>?): Int {
        /*if (context != null) {
            val db = SaveMyTripDatabase.getInstance(context)
            val count = db!!.itemDao().updateItem(Item.fromContentValues(contentValues!!))
            context.contentResolver.notifyChange(uri, null)
            return count
        }
        throw IllegalArgumentException("Failed to update row into $uri")
         */
        return 0
    }

    private fun provideEstate(values: ContentValues): Estate {
        // TODO ContentProvider tweaks
        val estate: Estate = Estate()
        if (values.containsKey("addedDate")) estate.addedDate = values.getAsLong("addedDate")
        if (values.containsKey("wasSold")) estate.wasSold = values.getAsBoolean("wasSold")
        if (values.containsKey("soldDate")) estate.soldDate = values.getAsLong("soldDate")
        if (values.containsKey("type")) estate.type = values.getAsString("type")
        if (values.containsKey("price")) estate.price = values.getAsInteger("price")
        if (values.containsKey("energyScore")) estate.energyScore = values.getAsInteger("energyScore")
        if (values.containsKey("surface")) estate.surface = values.getAsInteger("surface")
        if (values.containsKey("rooms")) estate.rooms = values.getAsInteger("rooms") //
        if (values.containsKey("bedrooms")) estate.bedrooms = values.getAsInteger("bedrooms")
        if (values.containsKey("bathrooms")) estate.bathrooms = values.getAsInteger("bathrooms")
        if (values.containsKey("address")) estate.street = values.getAsString("street")
        if (values.containsKey("zipCode")) estate.zipCode = values.getAsString("zipCode")
        if (values.containsKey("location")) estate.location = values.getAsString("location")
        if (values.containsKey("highSpeedInternet")) estate.highSpeedInternet = values.getAsBoolean("highSpeedInternet")
        if (values.containsKey("nearbyPlaces")) estate.nearbyPlaces = values.getAsString("nearbyPlaces")
        if (values.containsKey("description")) estate.description = values.getAsString("description")
        if (values.containsKey("mainPicturePath")) estate.mainPicturePath = values.getAsString("mainPicturePath")
        if (values.containsKey("agent")) estate.agent_id = values.getAsLong("agent_id")
        if (values.containsKey("id")) estate.id = values.getAsLong("id")
        return estate
    }

}
