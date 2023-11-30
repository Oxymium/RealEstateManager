package com.oxymium.realestatemanager.model

data class Step(
    var isSelected: Boolean = false,
    val id: Int,
    val number: Int,
    val title: String,
    val icon: Int
) {
    constructor(
        id: Int,
        number: Int,
        title: String,
        icon: Int,
    ) : this(false, id, number, title, icon)

}
