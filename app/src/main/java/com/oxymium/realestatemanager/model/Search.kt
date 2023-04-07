package com.oxymium.realestatemanager.model

// ------
// Search
// ------
data class Search(var startingDate: String? = null,
             var endingDate: String? = null,
             var type: String? = null,
             var energy: String? = null,
             var highSpeedInternet: String? = null,
             var available: String? = null,
             var startingDateSold: String? = null,
             var endingDateSold: String? = null,
             var minPrice: String? = null,
             var maxPrice: String? = null,
             var minSurface: String? = null,
             var maxSurface: String? = null,
             var minRooms: String? = null,
             var location: String? = null,
             var nearby: String? = null,
             var minPictures: String? = null,
             // Used to initialize
             val id: Long = 0
             ){

    // Base string query
    val baseQuery = "SELECT *, estate.id, COUNT(picture.id) AS nbPics FROM estate LEFT JOIN picture ON estate.id = picture.estate_id GROUP BY estate.id HAVING nbPics >="
    val fullQuery = generateFullSearchQuery()
    // Generate corresponding SQL argument

    private fun generateMinPicQuery(): String {
        val string: String = if (minPictures.isNullOrEmpty()){
            " 0"
        }else{
            " $minPictures"
        }
        return string
    }

    private fun generateStartingDateQuery(): String?{
        var string: String? = null
        if (!startingDate.isNullOrEmpty()){
            string = " addedDate >= $startingDate"}
        return string
    }
    private fun generateEndingDateQuery(): String?{
        var string: String? = null
        if (!endingDate.isNullOrEmpty()){
            string = " addedDate <= $endingDate"}
        return string
    }

    private fun generateTypeQuery(): String?{
        var string: String? = null
        if (!type.isNullOrEmpty()){
            string = " type LIKE '$type'"}
        return string
    }

    private fun generateEnergyQuery(): String?{
        var string: String? = null
        if (!energy.isNullOrEmpty()){
            string = " energyScore LIKE '$energy'"}
        return string
    }

    private fun generateInternetQuery(): String?{
        var string: String? = null
        if (!highSpeedInternet.isNullOrEmpty()){
            string = " highSpeedInternet = $highSpeedInternet"}
        return string
    }

    private fun generateAvailableQuery(): String?{
        var string: String? = null
        if (!available.isNullOrEmpty()){
            string = " wasSold = $available"}
        return string
    }

    private fun generateStartingDateSoldQuery(): String?{
        var string: String? = null
        if (!startingDateSold.isNullOrEmpty()){
            string = " soldDate >= $startingDateSold"}
        return string
    }

    private fun generateEndingDateSoldQuery(): String?{
        var string: String? = null
        if (!endingDateSold.isNullOrEmpty()){
            string = " soldDate <= $endingDateSold"}
        return string
    }

    private fun generateMinPriceQuery(): String?{
        var string: String? = null
        if (!minPrice.isNullOrEmpty()){
            string = " price >= $minPrice"}
        return string
    }

    private fun generateMaxPriceQuery(): String?{
        var string: String? = null
        if (!maxPrice.isNullOrEmpty()){
            string = " price <= $maxPrice"}
        return string
    }

    private fun generateMinSurfaceQuery(): String?{
        var string: String? = null
        if (!minSurface.isNullOrEmpty()){
            string = " surface >= $minSurface"}
        return string
    }

    private fun generateMaxSurfaceQuery(): String?{
        var string: String? = null
        if (!maxSurface.isNullOrEmpty()){
            string = " surface <= $maxSurface"}
        return string
    }

    private fun generateMinRoomsQuery(): String?{
        var string: String? = null
        if (!minRooms.isNullOrEmpty()){
            string = " rooms >= $minRooms"}
        return string
    }

    private fun generateLocationQuery(): String?{
        var string: String? = null
        if (!location.isNullOrEmpty()){
            string = " location LIKE '$location%'"}
        return string
    }

    private fun generateNearbyQuery(): String?{
        var string: String? = null
        if (!nearby.isNullOrEmpty()){
            string = " nearbyPlaces LIKE '$nearby%'"}
        return string
    }

    fun generateFullSearchQuery(): String{
        // Reset
        var generatedQuery = ""
        // Add base query to the full query
        generatedQuery += baseQuery
        // Add pictures amount to the query, by default always 0
        val minPicAmount = generateMinPicQuery()
        generatedQuery += minPicAmount
        // Add all remaining query arguments to a List
        val queryArguments = listOf(generateStartingDateQuery(), generateStartingDateQuery(), generateEndingDateQuery(), generateTypeQuery(), generateEnergyQuery(), generateInternetQuery(),
            generateAvailableQuery(), generateStartingDateSoldQuery(), generateEndingDateSoldQuery(), generateMinPriceQuery(), generateMaxPriceQuery(), generateMinSurfaceQuery(), generateMaxSurfaceQuery(),
            generateMinRoomsQuery(), generateLocationQuery(), generateNearbyQuery())
        // Remove all NULL elements from said list
        val nonNullList = queryArguments.filterNotNull()
        // Iterate through elements and add AND between parameters
        for (i in nonNullList){
            generatedQuery += " AND$i" }

        println("SEARCH OBJECT$queryArguments")

        return generatedQuery
    }

}