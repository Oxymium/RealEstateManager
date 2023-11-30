package com.oxymium.realestatemanager.model

import com.oxymium.realestatemanager.model.databaseitems.Estate

data class EstateState(
    val estate: Estate? = null,
    val isEdit: Boolean = false,
)