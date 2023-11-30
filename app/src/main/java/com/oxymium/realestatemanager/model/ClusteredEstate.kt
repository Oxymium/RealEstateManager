package com.oxymium.realestatemanager.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import com.oxymium.realestatemanager.model.databaseitems.Estate

class ClusteredEstate(val estate: Estate): ClusterItem {

    override fun getPosition(): LatLng {
        return LatLng(estate.latitude ?: 0.0, estate.longitude ?: 0.0)
    }

    override fun getTitle(): String {
        return "${estate.id}"
    }

    override fun getSnippet(): String {
        return "Type - " + estate.type + "\n" + "Price - $" + "${estate.price}"
    }
}