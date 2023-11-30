package com.oxymium.realestatemanager.model.mock

import com.oxymium.realestatemanager.model.databaseitems.Agent

// Generate a list of agents with given quantity
fun provideRandomAgentList(quantity: Int): List<Agent> {
    val randomAgents = mutableListOf<Agent>()
    repeat(quantity) {
        randomAgents.add(generateRandomAgent())
    }
    return randomAgents
}

// Generate one random Agent
fun generateRandomAgent(): Agent {
    return Agent(
        MOCK_FIRSTNAMES.random(),
        MOCK_LASTNAMES.random(),
        generateRandomPhoneNumberAsString(),
        MOCK_MAILS.random(),
        MOCK_AGENCIES.random()
    )
}

// Mocked firstnames
val MOCK_FIRSTNAMES = listOf(
    "Dawn",
    "Blake",
    "Mack",
    "Felicia",
    "Regina",
    "Jack",
    "Sabrina",
    "Alyce",
    "Marcelino",
    "Stacy",
    "Al",
    "Magnus",
    "Declan",
    "Liana",
    "Bella",
    "Clayton"
)

// Mocked lastnames
val MOCK_LASTNAMES = listOf(
    "Walls",
    "Welch",
    "Stout",
    "Harrington",
    "Mckenzie",
    "Campbell",
    "Padilla",
    "Rowe",
    "Hansen",
    "Stein",
    "Hines",
    "Leblanc",
    "Bird",
    "Garner",
    "Wong",
    "Wood"
)

// Mocked agencies
val MOCK_AGENCIES = listOf(
    "Paris",
    "Le Havre",
    "Annecy"
)

// Mocked mails
val MOCK_MAILS = listOf(
    "mock@gmailing.com",
    "mock@protonmailing.com"
)

// Random between 00 and 99
fun generateRandomNumberAsString(): String {
    val phoneNumberAsString = when (val number = (0..99).random()){
        // Add 0 for numbers 0..9
        in (0..9) -> "0$number"
        else -> number.toString()
    }
    return phoneNumberAsString
}

// Provide 10 digits phone number starting with 06
fun generateRandomPhoneNumberAsString(): String {
    return "06.${generateRandomNumberAsString()}.${generateRandomNumberAsString()}.${generateRandomNumberAsString()}.${generateRandomNumberAsString()}"
}

