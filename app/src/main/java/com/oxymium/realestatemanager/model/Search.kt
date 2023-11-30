package com.oxymium.realestatemanager.model

// ------
// Search
// ------
data class Search(
    var startAddedDate: String? = null,
    var endAddedDate: String? = null,
    var type: String? = null,
    var energy: String? = null,
    var energyRating: String? = null,
    var available: String? = null,
    var startSoldDate: String? = null,
    var endSoldDate: String? = null,
    var minPrice: String? = null,
    var maxPrice: String? = null,
    var minSurface: String? = null,
    var maxSurface: String? = null,
    var minRooms: String? = null,
    var minBedrooms: String? = null,
    var minBathrooms: String? = null,
    var location: String? = null,
    var nearby: String? = null,
    var minPictures: String? = null,
    var highSpeedInternet: String? = null,
    var furnished: String? = null,
    var disabledAccessibility: String? = null,
    var garden: String? = null,
    // Used to initialize
    val id: Long = 0
){

    // Base string query
    private val baseQuery = "SELECT *, estate.id, COUNT(picture.id) AS nbPics FROM estate LEFT JOIN picture ON estate.id = picture.estate_id GROUP BY estate.id HAVING nbPics >="
    // Generate corresponding SQL argument

    private fun generateMinPicQuery(): String {
        val string: String = if (minPictures.isNullOrEmpty()){
            " 0"
        }else{
            " $minPictures"
        }
        return string
    }

    private fun generateStartAddedDateQuery(): String?{
        var string: String? = null
        if (!startAddedDate.isNullOrEmpty()){
            string = " addedDate >= $startAddedDate"}
        return string
    }
    private fun generateEndAddedDateQuery(): String?{
        var string: String? = null
        if (!endAddedDate.isNullOrEmpty()){
            string = " addedDate <= $endAddedDate"}
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
            string = " energyType LIKE '$energy'"}
        return string
    }

    private fun generateEnergyRatingQuery(): String?{
        var string: String? = null
        if (!energyRating.isNullOrEmpty()){
            string = " energyRating LIKE '$energyRating'"}
        return string
    }

    private fun generateAvailableQuery(): String?{
        var string: String? = null
        if (!available.isNullOrEmpty()){
            string = " wasSold = $available"}
        return string
    }

    private fun generateStartSoldDateQuery(): String?{
        var string: String? = null
        if (!startSoldDate.isNullOrEmpty()){
            string = " soldDate >= $startSoldDate"}
        return string
    }

    private fun generateEndSoldDateQuery(): String?{
        var string: String? = null
        if (!endSoldDate.isNullOrEmpty()){
            string = " soldDate <= $endSoldDate"}
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

    private fun generateMinBedroomsQuery(): String?{
        var string: String? = null
        if (!minBedrooms.isNullOrEmpty()){
            string = " bedrooms >= $minBedrooms"}
        return string
    }

    private fun generateMinBathroomsQuery(): String?{
        var string: String? = null
        if (!minBathrooms.isNullOrEmpty()){
            string = " bathrooms >= $minBathrooms"}
        return string
    }

    private fun generateLocationQuery(): String?{
        var string: String? = null
        if (!location.isNullOrEmpty()){
            string = " location LIKE '$location'"}
        return string
    }

    private fun generateNearbyQuery(): String?{
        var string: String? = null
        if (!nearby.isNullOrEmpty()){
            string = " nearbyPlaces LIKE '%$nearby%'"}
        return string
    }

    private fun generateInternetQuery(): String?{
        var string: String? = null
        if (!highSpeedInternet.isNullOrEmpty()){
            string = " highSpeedInternet = $highSpeedInternet"}
        return string
    }

    private fun generateFurnishedQuery(): String?{
        var string: String? = null
        if (!furnished.isNullOrEmpty()){
            string = " furnished = '$furnished'" }
        return string
    }

    private fun generateDisabledAccessibilityQuery(): String?{
        var string: String? = null
        if (!disabledAccessibility.isNullOrEmpty()){
            string = " disabledAccessibility = '$disabledAccessibility'" }
        return string
    }

    private fun generateGardenQuery(): String?{
        var string: String? = null
        if (!garden.isNullOrEmpty()){
            string = " garden = '$garden'" }
        return string
    }

    fun generateFullSearchQuery(quickSearch: String?): String{

        val simpleSQLiteQuery: String?

        if (quickSearch != null){
            val queryString = "SELECT * FROM estate WHERE location LIKE '%$quickSearch%' " +
                    "OR type LIKE '%$quickSearch%' " +
                    "OR price LIKE '%$quickSearch%' " +
                    "OR surface LIKE '%$quickSearch%' " +
                    "OR energyRating like '%$quickSearch%' " +
                    "OR nearbyPlaces LIKE '%$quickSearch%' " +
                    "OR id like '%$quickSearch%'"
            simpleSQLiteQuery = queryString
        }else {
            // Reset
            var generatedQuery = ""
            // Add base query to the full query
            generatedQuery += baseQuery
            // Add pictures amount to the query, by default always 0
            val minPicAmount = generateMinPicQuery()
            generatedQuery += minPicAmount
            // Add all remaining query arguments to a List
            val queryArguments = listOf(
                generateStartAddedDateQuery(),
                generateEndAddedDateQuery(),
                generateTypeQuery(),
                generateEnergyQuery(),
                generateEnergyRatingQuery(),
                generateAvailableQuery(),
                generateStartSoldDateQuery(),
                generateEndSoldDateQuery(),
                generateMinPriceQuery(),
                generateMaxPriceQuery(),
                generateMinSurfaceQuery(),
                generateMaxSurfaceQuery(),
                generateMinRoomsQuery(),
                generateMinBedroomsQuery(),
                generateMinBathroomsQuery(),
                generateLocationQuery(),
                generateNearbyQuery(),
                generateInternetQuery(),
                generateFurnishedQuery(),
                generateDisabledAccessibilityQuery(),
                generateGardenQuery()
            )
            // Remove all NULL elements from said list
            val nonNullList = queryArguments.filterNotNull()
            // Iterate through elements and add AND between parameters
            for (i in nonNullList) generatedQuery += " AND$i"
            simpleSQLiteQuery = generatedQuery
        }

        return simpleSQLiteQuery
    }
}