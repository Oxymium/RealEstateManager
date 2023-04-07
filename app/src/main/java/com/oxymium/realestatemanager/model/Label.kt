package com.oxymium.realestatemanager.model

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