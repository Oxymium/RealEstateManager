package com.oxymium.realestatemanager.features.map

import android.content.Context
import android.graphics.Color
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.oxymium.realestatemanager.model.ClusteredEstate

class EstateClusterRenderer(
    context: Context,
    googleMap: GoogleMap,
    clusterManager: ClusterManager<ClusteredEstate>
) : DefaultClusterRenderer<ClusteredEstate>(context, googleMap, clusterManager) {

    // RENDER CLUSTERED ITEM COLOR
    override fun onClusterItemRendered(clusterItem: ClusteredEstate, marker: Marker) {
        super.onClusterItemRendered(clusterItem, marker)
        // Marker color logic based on sold status
        when (clusterItem.estate.wasSold) {
            true -> marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            false -> marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            else -> marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        }
    }

    override fun getColor(clusterSize: Int): Int {
        return Color.parseColor("#FF00B8")
    }

    override fun onBeforeClusterRendered(cluster: Cluster<ClusteredEstate>, markerOptions: MarkerOptions) {
        // Display the cluster marker with a custom icon
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
    }
}