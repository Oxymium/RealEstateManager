package com.oxymium.realestatemanager.model

sealed class CategoryHelper {

    abstract val categoryMessage: String?

    object CategoryEnergyClass: CategoryHelper(){
        override val categoryMessage = "Energy class"
    }
    object CategoryRooms: CategoryHelper(){
        override val categoryMessage = "Rooms"
    }
    object CategoryBedrooms: CategoryHelper(){
        override val categoryMessage = "Bedrooms"
    }
    object CategoryBathrooms: CategoryHelper(){
        override val categoryMessage = "Bathrooms"
    }
    object CategoryInternet: CategoryHelper(){
        override val categoryMessage = "High-speed internet"
    }
    object CategoryFurnished: CategoryHelper(){
        override val categoryMessage = "Furnished"
    }
    object CategoryGarden: CategoryHelper(){
        override val categoryMessage = "Garden"
    }
    object CategoryDisabledAccessibility: CategoryHelper(){
        override val categoryMessage = "Disabled Accessibility"
    }

}