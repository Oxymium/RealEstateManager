package com.oxymium.realestatemanager.features.map

import com.oxymium.realestatemanager.model.ClusteredEstate

interface OnMarkerClickListener {
    fun onMarkerClick(clusteredEstate: ClusteredEstate)
}