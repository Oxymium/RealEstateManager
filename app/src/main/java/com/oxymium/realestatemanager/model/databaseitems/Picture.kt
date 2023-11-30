package com.oxymium.realestatemanager.model.databaseitems

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

// --------------------
// Picture (Model class)
// --------------------

@Entity(
    tableName = "picture",
    foreignKeys = [
        ForeignKey(
            entity = Estate::class,
            parentColumns = ["id"],
            childColumns = ["estate_id"],
            onDelete = ForeignKey.CASCADE
        )])
data class Picture(
    @ColumnInfo(name = "path")
    var path: String,
    @ColumnInfo(name = "comment")
    var comment:String,
    @ColumnInfo(name = "estate_id", index = true)
    var estate_id:Long? = null,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Long? = null
)
