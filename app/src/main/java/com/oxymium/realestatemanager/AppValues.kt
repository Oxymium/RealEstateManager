package com.oxymium.realestatemanager

import com.oxymium.realestatemanager.model.Step

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

