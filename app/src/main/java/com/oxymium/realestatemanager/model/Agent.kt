package com.oxymium.realestatemanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

// -----
// Agent
// -----

@Entity(tableName = "agent")
data class Agent(
    @Ignore
    override var isSelected: Boolean,
    @ColumnInfo(name = "first_name")
    var firstName: String,
    @ColumnInfo(name = "last_name")
    var lastName: String,
    @ColumnInfo(name = "phone_number")
    var phoneNumber: String,
    @PrimaryKey(autoGenerate = true) override var id: Long?
): SelectableItem

{
    constructor(firstName: String, lastName: String, phoneNumber: String) : this(false, firstName, lastName, phoneNumber, null)
    // No-arg constructor
    constructor() : this(false, "", "","", 0)
}

