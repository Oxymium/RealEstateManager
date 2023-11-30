package com.oxymium.realestatemanager.model

import java.util.Random

data class Label(
    override var isSelected: Boolean = false,
    val label: String,
    override var id: Long?)
    : SelectableItem

{
    constructor(label: String) : this(false, label, null)
    // No-arg constructor
    constructor() : this(false, "", 0)
}

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