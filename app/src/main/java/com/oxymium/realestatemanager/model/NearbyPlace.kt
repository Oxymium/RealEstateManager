package com.oxymium.realestatemanager.model

data class NearbyPlace(
    val id: Int? = null,
    val content: String? = null,
    val isSelected: Boolean? = false
)

// List of Nearby Places -> to a single String
fun List<NearbyPlace>.toConcatenatedString(): String {
    return joinToString(",") { it.content.orEmpty() }
}

// Single string -> to a List<NearbyPlace>
fun String.toNearbyPlaceList(): List<NearbyPlace> {
    return split(",").map { NearbyPlace(content = it) }
}