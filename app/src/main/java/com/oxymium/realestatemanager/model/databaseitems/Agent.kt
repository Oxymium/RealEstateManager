package com.oxymium.realestatemanager.model.databaseitems

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.oxymium.realestatemanager.model.SelectableItem

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
    @ColumnInfo(name = "mail")
    var mail: String,
    @ColumnInfo(name = "agency")
    var agency: String,
    @PrimaryKey(autoGenerate = true)
    override var id: Long?
): SelectableItem

{
    constructor(firstName: String, lastName: String, phoneNumber: String, mail: String, agency: String) : this(false, firstName, lastName, phoneNumber, mail, agency, null)
    // No-arg constructor
    constructor() : this(false, "", "","", "", "", 0)
}

