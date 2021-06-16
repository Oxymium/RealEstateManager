package com.oxymium.realestatemanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// -------------------
// Estate (Model class)
// -------------------

@Entity(tableName = "estates")
data class Estate(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Long,
    @ColumnInfo(name = "type")
    var type:String,
    @ColumnInfo(name = "price")
    var price: Int,
    @ColumnInfo(name = "surface")
    var surface:Int,
    @ColumnInfo(name = "energy")
    var energy: String,
    @ColumnInfo(name = "rooms")
    var rooms:Int,
    @ColumnInfo(name = "location")
    var location: String
    )

