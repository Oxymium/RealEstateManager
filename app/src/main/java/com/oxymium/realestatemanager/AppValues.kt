package com.oxymium.realestatemanager

import com.oxymium.realestatemanager.model.MenuStep

// Turn on/off the static map
var ENABLE_STATIC_MAP = false

// Amount of secondary pictures limit
const val SECONDARY_PICTURES_AMOUNT_LIMIT = 9

// Amount of Agents pre-inserted into the DB
const val PRE_INSERTED_AGENT_AMOUNT = 14

// Provide Steps for Create/Edit
val CREATE_MENU_STEPS = listOf(
    MenuStep(
        id = 0,
        title = "Overview",
        icon = R.drawable.overview),
    MenuStep(
        id = 1,
        title = "Agent",
        icon = R.drawable.agent),
    MenuStep(
        id = 2,
        title = "Type",
        icon = R.drawable.type),
    MenuStep(
        id = 3,
        title = "Values",
        icon = R.drawable.values),
    MenuStep(
        id = 4,
        title = "Main picture",
        icon = R.drawable.picture),
    MenuStep(
        id = 5,
        title = "Secondary pictures",
        icon = R.drawable.picture_multiple),
    MenuStep(
        id = 6,
        title = "Miscellaneous",
        icon = R.drawable.miscellaneous),
    MenuStep(
        id = 7,
        title = "Address",
        icon = R.drawable.map),
    MenuStep(
        id = 8,
        title = "Nearby places",
        icon = R.drawable.nearby_places),
)

// Provide Steps for Tools
val TOOL_MENU_STEPS = listOf(
    MenuStep(
        id = 0,
        title = "Currency Converter",
        icon = R.drawable.currency),
    MenuStep(
        id = 1,
        title = "Loan Simulator",
        icon = R.drawable.chart_pie),
    MenuStep(
        id = 2,
        title = "Dev tools",
        icon = R.drawable.dev),
)


