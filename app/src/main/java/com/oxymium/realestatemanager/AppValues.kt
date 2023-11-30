package com.oxymium.realestatemanager

import com.oxymium.realestatemanager.model.Label
import com.oxymium.realestatemanager.model.Step
import java.util.Random

// Turn on/off the static map
var ENABLE_STATIC_MAP = false

// Amount of secondary pictures limit
const val SECONDARY_PICTURES_AMOUNT_LIMIT = 9

// Amount of Agents pre-inserted into the DB
const val PRE_INSERTED_AGENT_AMOUNT = 14

// Provide Steps for Create/Edit
val CREATE_STEPS = listOf(
    Step(0, 0, "Overview", R.drawable.overview),
    Step(1, 1, "Agent", R.drawable.agent),
    Step(2, 2, "Type", R.drawable.type),
    Step(3, 3, "Values", R.drawable.values),
    Step(4, 4, "Main picture", R.drawable.picture),
    Step(5, 5, "Secondary pictures", R.drawable.picture_multiple),
    Step(6, 6, "Miscellaneous", R.drawable.miscellaneous),
    Step(7, 7, "Address", R.drawable.map),
    Step(8, 8, "Nearby places", R.drawable.labels),
    )

// Provide Steps for Tools
val TOOLS_STEPS = listOf(
    Step(1, 1, "Currency Converter", R.drawable.currency),
    Step( 2, 2, "Loan Simulator", R.drawable.chart_pie),
    Step(3, 3, "Dev tools", R.drawable.dev)
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

//fun generateRandomNearbyPlacesString(): String?{
//    //var test: String? = null
//    //for (i in 1..NEARBY_PLACES.size){
//        //test += NEARBY_PLACES.toConcatenatedString()
//    //}
//    //return test
//}
//

