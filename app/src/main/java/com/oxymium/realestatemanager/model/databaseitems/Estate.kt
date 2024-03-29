package com.oxymium.realestatemanager.model.databaseitems

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// -------------------
// Estate (Model class)
// -------------------

@Entity(tableName = "estate",
        foreignKeys = [
        androidx.room.ForeignKey(
            entity = Agent::class,
            parentColumns = ["id"],
            childColumns = ["agent_id"],
            onDelete = androidx.room.ForeignKey.NO_ACTION
        )])

data class Estate(
    @ColumnInfo(name = "addedDate")
    var addedDate: Long?,
    @ColumnInfo(name = "wasSold")
    var wasSold: Boolean?,
    @ColumnInfo(name = "soldDate")
    var soldDate: Long?,
    @ColumnInfo(name = "type")
    var type:String?,
    @ColumnInfo(name = "price")
    var price: Int?,
    @ColumnInfo(name = "energyScore")
    var energyScore: Int?,
    @ColumnInfo(name = "energyRating")
    var energyRating: String?,
    @ColumnInfo(name = "surface")
    var surface:Int?,
    @ColumnInfo(name = "rooms")
    var rooms:Int?,
    @ColumnInfo(name = "bedrooms")
    var bedrooms:Int?,
    @ColumnInfo(name = "bathrooms")
    var bathrooms:Int?,
    @ColumnInfo(name = "street")
    var street: String?,
    @ColumnInfo(name = "zipCode")
    var zipCode: String?,
    @ColumnInfo(name = "location")
    var location: String?,
    @ColumnInfo(name = "latitude")
    var latitude: Double?,
    @ColumnInfo(name = "longitude")
    var longitude: Double?,
    @ColumnInfo(name = "highSpeedInternet")
    var highSpeedInternet: Boolean?,
    @ColumnInfo(name = "furnished")
    var furnished: Boolean?,
    @ColumnInfo(name = "disabledAccessibility")
    var disabledAccessibility: Boolean?,
    @ColumnInfo(name = "garden")
    var garden: Boolean?,
    @ColumnInfo(name = "nearbyPlaces")
    var nearbyPlaces: String?,
    @ColumnInfo(name = "description")
    var description: String?,
    @ColumnInfo(name = "mainPicturePath")
    var mainPicturePath: String?,
    @ColumnInfo(name = "agent_id", index = true)
    var agent_id: Long?,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null)

{
    // No-arg constructor
    constructor() : this(
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null)
}









