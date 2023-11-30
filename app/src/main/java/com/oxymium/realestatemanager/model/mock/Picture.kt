package com.oxymium.realestatemanager.model.mock

import com.oxymium.realestatemanager.model.databaseitems.Picture

const val ESTATE_PLACEHOLDER_PATH = "android.resource://com.oxymium.realestatemanager/drawable/estate_"
const val KITCHEN_PLACEHOLDER_PATH = "android.resource://com.oxymium.realestatemanager/drawable/kitchen_"
const val BEDROOMS_PLACEHOLDER_PATH = "android.resource://com.oxymium.realestatemanager/drawable/bedroom_"
const val BATHROOMS_PLACEHOLDER_PATH = "android.resource://com.oxymium.realestatemanager/drawable/bathroom_"
const val MIN_PLACEHOLDER_PICTURE_NUMBER = 1
const val MAX_PLACEHOLDER_PICTURE_NUMBER = 20

fun provideRandomPicturePath(): String {
    val randomPlaceHolderPictureNumber = (MIN_PLACEHOLDER_PICTURE_NUMBER..MAX_PLACEHOLDER_PICTURE_NUMBER).random()
    return "$ESTATE_PLACEHOLDER_PATH$randomPlaceHolderPictureNumber"
}

fun provideRandomListPicture(quantity: Int): List<Picture> {
    val pictures = mutableListOf<Picture>()
    repeat(quantity) {
        pictures.add(generateOneRandomPicture())
    }
    return pictures
}

fun generateOneRandomPicture(): Picture {
    return Picture(
        provideRandomPicturePath(),
        COMMENTS.random(),
        null,
        null
        )
}

val COMMENTS = listOf("Room", "Kitchen", "Bathroom", "Bedroom", "Garden", "Pool")