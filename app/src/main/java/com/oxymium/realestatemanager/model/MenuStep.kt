package com.oxymium.realestatemanager.model


data class MenuStep(
    var isSelected: Boolean = false,
    val id: Int,
    val title: String,
    val icon: Int
)