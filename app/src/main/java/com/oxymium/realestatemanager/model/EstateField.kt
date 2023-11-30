package com.oxymium.realestatemanager.model

sealed class EstateField {

    data class Date(val date: Long?): EstateField()
    data class AgentId(val agentId: Long?): EstateField()
    data class Type(val type: String?): EstateField()

    data class Price(val price: Int?): EstateField()
    data class Surface(val surface: Int?): EstateField()
    data class Rooms(val rooms: Int?): EstateField()
    data class Bedrooms(val bedrooms: Int?): EstateField()
    data class Bathrooms(val bathrooms: Int?): EstateField()
    data class EnergyScore(val energyScore: Int?): EstateField()
    data class EnergyRating(val energyRating: String?): EstateField()

    data class MainPicturePath(val mainPicturePath: String?): EstateField()

    data class Description(val description: String?): EstateField()
    data class HighSpeedInternet(val hasHighSpeedInternet: Boolean?): EstateField()
    data class Furnished(val isFurnished: Boolean?): EstateField()
    data class Garden(val hasGarden: Boolean?): EstateField()
    data class DisabledAccessibility(val hasDisabledAccessibility: Boolean?): EstateField()

    data class Street(val street: String?): EstateField()
    data class ZipCode(val zipCode: String?): EstateField()
    data class Location(val location: String?): EstateField()
    data class Latitude(val latitude: Double?): EstateField()
    data class Longitude(val longitude: Double?): EstateField()
    data class NearbyPlaces(val nearbyPlaces: String?): EstateField()
}
