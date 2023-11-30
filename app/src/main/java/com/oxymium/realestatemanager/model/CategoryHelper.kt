package com.oxymium.realestatemanager.model

sealed class CategoryHelper {

    abstract val categoryMessage: String

    data object CategoryEnergyClass: CategoryHelper(){
        override val categoryMessage = "Energy class"
    }
    data object CategoryRooms: CategoryHelper(){
        override val categoryMessage = "Rooms"
    }
    data object CategoryBedrooms: CategoryHelper(){
        override val categoryMessage = "Bedrooms"
    }
    data object CategoryBathrooms: CategoryHelper(){
        override val categoryMessage = "Bathrooms"
    }
    data object CategoryInternet: CategoryHelper(){
        override val categoryMessage = "High-speed internet"
    }
    data object CategoryFurnished: CategoryHelper(){
        override val categoryMessage = "Furnished"
    }
    data object CategoryGarden: CategoryHelper(){
        override val categoryMessage = "Garden"
    }
    data object CategoryDisabledAccessibility: CategoryHelper(){
        override val categoryMessage = "Disabled Accessibility"
    }
}