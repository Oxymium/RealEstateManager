package com.oxymium.realestatemanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

import android.content.ContentValues




// -------------------
// Estate (Model class)
// -------------------

@Entity(tableName = "estate")
data class Estate(
    @ColumnInfo(name = "addedDate")
    var addedDate:Long,
    @ColumnInfo(name = "wasSold")
    var wasSold: Boolean,
    @ColumnInfo(name = "soldDate")
    var soldDate: Long,
    @ColumnInfo(name = "type")
    var type:String,
    @ColumnInfo(name = "price")
    var price: Int,
    @ColumnInfo(name = "energyScore")
    var energy: String,
    @ColumnInfo(name = "surface")
    var surface:Int,
    @ColumnInfo(name = "rooms")
    var rooms:Int,
    @ColumnInfo(name = "bedrooms")
    var bedrooms:Int,
    @ColumnInfo(name = "bathrooms")
    var bathrooms:Int,
    @ColumnInfo(name = "address")
    var address: String,
    @ColumnInfo(name = "zipCode")
    var zipCode: Int,
    @ColumnInfo(name = "location")
    var location: String,
    @ColumnInfo(name = "highSpeedInternet")
    var highSpeedInternet: Boolean,
    @ColumnInfo(name = "nearbyPlaces")
    var nearbyPlaces: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "mainPicturePath")
    var mainPicturePath: String,
    @ColumnInfo(name = "agent")
    var agent: String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
    )

    // Methods to expose Estate
    fun fromContentValues(values: ContentValues): Estate? {
        // TODO ContentProvider tweaks
        val estate: Estate? = null
        if (values.containsKey("addedDate")) estate?.addedDate = values.getAsLong("addedDate")
        if (values.containsKey("wasSold")) estate?.wasSold = values.getAsBoolean("wasSold")
        if (values.containsKey("soldDate")) estate?.soldDate = values.getAsLong("soldDate")
        if (values.containsKey("type")) estate?.type = values.getAsString("type")
        if (values.containsKey("price")) estate?.price = values.getAsInteger("price")
        if (values.containsKey("energyScore")) estate?.energy = values.getAsString("energyScore")
        if (values.containsKey("surface")) estate?.surface = values.getAsInteger("surface")
        if (values.containsKey("rooms")) estate?.rooms = values.getAsInteger("rooms") //
        if (values.containsKey("bedrooms")) estate?.bedrooms = values.getAsInteger("bedrooms")
        if (values.containsKey("bathrooms")) estate?.bathrooms = values.getAsInteger("bathrooms")
        if (values.containsKey("address")) estate?.address = values.getAsString("address")
        if (values.containsKey("zipCode")) estate?.zipCode = values.getAsInteger("zipCode")
        if (values.containsKey("location")) estate?.location = values.getAsString("location")
        if (values.containsKey("highSpeedInternet")) estate?.highSpeedInternet = values.getAsBoolean("highSpeedInternet")
        if (values.containsKey("nearbyPlaces")) estate?.nearbyPlaces = values.getAsString("nearbyPlaces")
        if (values.containsKey("description")) estate?.description = values.getAsString("description")
        if (values.containsKey("mainPicturePath")) estate?.mainPicturePath = values.getAsString("mainPicturePath")
        if (values.containsKey("agent")) estate?.agent = values.getAsString("agent")
        if (values.containsKey("id")) estate?.id = values.getAsLong("id")

        return estate
}





