package com.oxymium.realestatemanager.model

data class Step(
    var isSelected: Boolean = false,
    val id: Int,
    val number: Int,
    val picture: Int
) {
    constructor(
        id: Int,
        number: Int,
        picture: Int,
    ) : this(false, id, number, picture)

}
