package com.oxymium.realestatemanager

import com.oxymium.realestatemanager.model.Agent
import com.oxymium.realestatemanager.model.Estate
import com.oxymium.realestatemanager.model.Label
import com.oxymium.realestatemanager.model.Picture
import java.util.Random

// Turn on/off the static map
const val ENABLE_STATIC_MAP = false

// Amount of secondary pictures limit
const val SECONDARY_PICTURES_AMOUNT_LIMIT = 9

// Provide random phone number on compile

fun generateRandomNumberAsString(): String{
    val numberAsString = when (val number = (0..99).random()){
        // Add 0 for numbers 0..9
        in (0..9) -> "0$number"
        else -> number.toString()
    }
    return numberAsString
}

fun generateRandomPhoneNumber(): String{
    return "06.${generateRandomNumberAsString()}.${generateRandomNumberAsString()}.${generateRandomNumberAsString()}.${generateRandomNumberAsString()}"
}

// Provide random Agents (phone numbers generated at runtime to prevent privacy issues)
val MOCK_FIRSTNAMES = listOf("Dawn", "Blake", "Mack", "Felicia", "Regina", "Jack", "Sabrina", "Alyce", "Marcelino", "Stacy", "Al", "Magnus", "Declan", "Liana", "Bella", "Clayton" )
val MOCK_LASTNAMES = listOf("Walls", "Welch", "Stout", "Harrington", "Mckenzie", "Campbell", "Padilla", "Rowe", "Hansen", "Stein", "Hines", "Leblanc", "Bird", "Garner", "Wong", "Wood")
fun provideRandomAgents(quantity: Int): List<Agent>{
    val agents = mutableListOf<Agent>()
    repeat(quantity) {
        agents.add(Agent(MOCK_FIRSTNAMES.random(), MOCK_LASTNAMES.random(), generateRandomPhoneNumber()))
    }
    return agents
}

// Provide random estate types
val ESTATE_TYPES = listOf(
    Label("Apartment"), Label("Bungalow"), Label("Castle"), Label("Cooperative"), Label("Cottage"),
    Label("Condominium"), Label("Duplex"), Label("Farm"), Label("Houseboat"), Label("Igloo"),
    Label("Loft"), Label("Mansion"), Label("Manor"), Label("Penthouse"), Label("Ranch"),
    Label("Single-Family Home"), Label("Studio"), Label("Townhouse"), Label("Treehouse"), Label("Villa")
)

// Provide random estate types
val NEARBY_PLACES = listOf(
    Label("Airport"), Label("Amusement Park"), Label("Aquarium"), Label("Art Studio"), Label("Bar"),
    Label("Beach"), Label("Botanical Garden"), Label("Brewery"), Label("Bus Station"), Label("Cafe"),
    Label("Cathedral"), Label("Church"), Label("Cinema"), Label("Concert Hall"), Label("Distillery"),
    Label("Factory"), Label("Farmers Market"), Label("Food Market"), Label("Gallery"), Label("Gym"),
    Label("Harbor"), Label("Historic Site"), Label("Hotel"), Label("Library"), Label("Mosque"),
    Label("Museum"), Label("Nature Reserve"), Label("Park"), Label("Planetarium"), Label("Restaurant"),
    Label("School"), Label("Shopping Mall"), Label("Spa"), Label("Stadium"), Label("Synagogue"),
    Label("Swimming Pool"), Label("Theatre"), Label("Train Station"), Label("Vineyard"), Label("Zoo")
)

fun List<Label>.generateRandomLabelString(): String {
    val random = Random()
    val labelStrings = mutableListOf<String>()
    val maxLabels = size
    val numLabels = random.nextInt(maxLabels) + 1

    for (i in 1..numLabels) {
        var labelString: String
        do {
            val labelIndex = random.nextInt(maxLabels)
            labelString = get(labelIndex).label
        } while (labelString in labelStrings)
        labelStrings.add(labelString)
    }

    return labelStrings.joinToString(separator = " ")
}

// List of Labels -> to a single String
fun List<Label>.toConcatenatedString(): String {
    return joinToString(" ") { it.label }
}

// Single string -> to a List<Labels>
fun String.toLabelList(): List<Label> {
    return split(",").map { Label(label = it, isSelected = true, id = 0) }
}

// Single string -> to a List<Labels>
fun String.toLabelListTest(): List<String> {
    return split(" ")
}

fun generateRandomNearbyPlacesString(): String?{
    var test: String? = null
    for (i in 1..NEARBY_PLACES.size){
        test += NEARBY_PLACES.toConcatenatedString()
    }
    return test
}

const val PLACEHOLDER_PATH = "android.resource://com.oxymium.realestatemanager/drawable/estate_placeholder"
val RANDOM_PICTURES = listOf(
    Picture("${PLACEHOLDER_PATH}2", "Room"),
    Picture("${PLACEHOLDER_PATH}3", "Kitchen"),
    Picture("${PLACEHOLDER_PATH}4", "Bathroom"),
    Picture("${PLACEHOLDER_PATH}5", "Room 2"),
    Picture("${PLACEHOLDER_PATH}6", "Garden"),
    Picture("${PLACEHOLDER_PATH}7", "Pool"),
    Picture("${PLACEHOLDER_PATH}8", "Gym"),
    Picture("${PLACEHOLDER_PATH}9", "Bathroom")
)

// Random for testing purposes
fun generateRandomEstate(): Estate {

    val randomSoldStatus = arrayOf(true, false).random()
    val randomType = ESTATE_TYPES.random().label
    val randomPrice = arrayOf(100000, 150000, 550200, 660500, 1000000, 2000000, 2500000).random()
    val randomSurface = (0..200).random()
    val randomRooms = (1..20).random()
    val randomBedrooms = (1..15).random()
    val randomBathrooms = (1..10).random()
    val randomEnergyScore = (1..701).random()

    val randomStreets = arrayOf("21 rue de la Paix", "19 rue des Tulipes", "07 avenue des bois fleuris").random()
    val randomZipCodes = arrayOf("00001", "00002", "00003", "00004", "00005").random()
    val randomLocations = arrayOf("New-York", "Paris", "Tokyo", "Singapore", "Moscow", "Seoul", "Sidney").random()

    val randomHighSpeedInternet = arrayOf(true, false).random()
    val randomGarden = arrayOf(true, false).random()
    val randomDisabledAccessibility = arrayOf(true, false).random()
    val randomFurnished = arrayOf(true, false).random()

    val randomNearbyPlaces = NEARBY_PLACES.generateRandomLabelString()
    val randomDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."

    val randomMainPictures = arrayOf(
        "${PLACEHOLDER_PATH}2",
        "${PLACEHOLDER_PATH}3",
        "${PLACEHOLDER_PATH}4",
        "${PLACEHOLDER_PATH}5",
        "${PLACEHOLDER_PATH}6",
        "${PLACEHOLDER_PATH}7",
        "${PLACEHOLDER_PATH}8",
        "${PLACEHOLDER_PATH}9",
    ).random()

    // TODO random agent for mock
    val randomAgentId = (1L..6L).random()

    return Estate(
        1631085363547,
        randomSoldStatus,
        1631085363547,
        randomType,
        randomPrice,
        randomEnergyScore,
        randomSurface,
        randomRooms,
        randomBedrooms,
        randomBathrooms,
        randomStreets,
        randomZipCodes,
        randomLocations,
        randomHighSpeedInternet,
        randomGarden,
        randomDisabledAccessibility,
        randomFurnished,
        randomNearbyPlaces,
        randomDescription,
        randomMainPictures,
        randomAgentId
    )

}

