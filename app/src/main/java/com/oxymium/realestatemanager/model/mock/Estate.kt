package com.oxymium.realestatemanager.model.mock

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.oxymium.realestatemanager.PRE_INSERTED_AGENT_AMOUNT
import com.oxymium.realestatemanager.model.ENERGIES
import com.oxymium.realestatemanager.model.Label
import com.oxymium.realestatemanager.model.databaseitems.Estate
import com.oxymium.realestatemanager.model.toConcatenatedString
import java.util.Calendar
import kotlin.random.Random

fun generateOneRandomEstate(): Estate {

    val randomAddedDate = (DATE..NOW).random() // get reference to randomized date
    val randomWasSold = Random.nextBoolean() // if random estate is sold, then provide soldDate that is superior to addedDate
    val randomSoldDate = if (randomWasSold) (randomAddedDate + 1 ..NOW).random() else null // +1 for strictly superior (unlikely but possible scenario)
    val randomLoremIpsum = LoremIpsum((0..50).random()).toString()
    val randomEnergy = ENERGIES.random()
    val randomNearbyPlaces = NEARBY_PLACES.shuffled().take((1..NEARBY_PLACES.size).random()).toConcatenatedString()

    return Estate(
        randomAddedDate,
        randomWasSold,
        randomSoldDate,
        ESTATE_TYPES.random().label,
        (MIN_PRICE..MAX_PRICE).random(),
        randomEnergy.score.random(),
        randomEnergy.rating,
        (MIN_SURFACE..MAX_SURFACE).random(),
        (MIN_ROOMS..MAX_ROOMS).random(),
        (MIN_BEDROOMS..MAX_BEDROOMS).random(),
        (MIN_BATHROOMS..MAX_BATHROOMS).random(),
        STREETS.random(),
        ZIPCODES.random(),
        LOCATIONS.random(),
        10.0,
        10.0,
        Random.nextBoolean(),
        Random.nextBoolean(),
        Random.nextBoolean(),
        Random.nextBoolean(),
        randomNearbyPlaces,
        randomLoremIpsum,
        provideRandomPicturePath(),
        (0..PRE_INSERTED_AGENT_AMOUNT).random().toLong() // Match number of agent inserted inside the DB
    )
}

const val DATE = 1577836800000L // 1st jan 2020
val NOW = Calendar.getInstance().timeInMillis
const val MIN_PRICE = 40000
const val MAX_PRICE = 99999999
const val MIN_SURFACE = 10
const val MAX_SURFACE = 9999
const val MIN_ROOMS = 1
const val MAX_ROOMS = 20
const val MIN_BEDROOMS = 1
const val MAX_BEDROOMS = 15
const val MIN_BATHROOMS = 1
const val MAX_BATHROOMS = 10

val ESTATE_TYPES = listOf(
    Label("Apartment"),
    Label("Bungalow"),
    Label("Castle"),
    Label("Cooperative"),
    Label("Cottage"),
    Label("Condominium"),
    Label("Duplex"),
    Label("Farm"),
    Label("Houseboat"),
    Label("Igloo"),
    Label("Loft"),
    Label("Mansion"),
    Label("Manor"),
    Label("Penthouse"),
    Label("Ranch"),
    Label("Single-Family Home"),
    Label("Studio"),
    Label("Townhouse"),
    Label("Treehouse"),
    Label("Villa")
)

val NEARBY_PLACES = listOf(
    Label("Airport"),
    Label("Amusement Park"),
    Label("Aquarium"),
    Label("Art Studio"),
    Label("Bar"),
    Label("Beach"),
    Label("Botanical Garden"),
    Label("Brewery"),
    Label("Bus Station"),
    Label("Cafe"),
    Label("Cathedral"),
    Label("Church"),
    Label("Cinema"),
    Label("Concert Hall"),
    Label("Distillery"),
    Label("Factory"),
    Label("Farmers Market"),
    Label("Food Market"),
    Label("Gallery"),
    Label("Gym"),
    Label("Harbor"),
    Label("Historic Site"),
    Label("Hotel"),
    Label("Library"),
    Label("Mosque"),
    Label("Museum"),
    Label("Nature Reserve"),
    Label("Park"),
    Label("Planetarium"),
    Label("Restaurant"),
    Label("School"),
    Label("Shopping Mall"),
    Label("Spa"),
    Label("Stadium"),
    Label("Synagogue"),
    Label("Swimming Pool"),
    Label("Theatre"),
    Label("Train Station"),
    Label("Vineyard"),
    Label("Zoo")
)

val ENERGY_RATINGS = listOf("A", "B", "C", "D", "E", "F", "G")

val STREETS = listOf(
    "12 Rue de l'Étoile Brillante",
    "45 Avenue des Champs Élysées",
    "78 Rue du Petit Paradis",
    "32 Boulevard des Roses Rouges",
    "55 Rue de la Belle Vue",
    "21 Avenue des Lumières Douces",
    "66 Rue du Jardin Enchanté")

val ZIPCODES = listOf("00001", "00002", "00003", "00004", "00005")

val LOCATIONS = listOf("Paris", "Tokyo", "Singapore", "London", "Moscow", "New-York", "Sidney")