package com.oxymium.realestatemanager.features.create

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oxymium.realestatemanager.databinding.ItemNearbyPlaceBinding
import com.oxymium.realestatemanager.model.NearbyPlace

class NearbyPlaceViewHolder(val binding: ItemNearbyPlaceBinding):
    RecyclerView.ViewHolder(binding.root) {

    fun bind(nearbyPlace: NearbyPlace, nearbyPlaceListener: NearbyPlaceListener) {

        // REQUIRED WITH DATA BINDING
        binding.executePendingBindings()

        // BIND DATA
        binding.nearbyPlace = nearbyPlace
        binding.nearbyPlaceListener = nearbyPlaceListener

    }

    companion object {

        fun from(parent: ViewGroup): NearbyPlaceViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemNearbyPlaceBinding.inflate(layoutInflater, parent, false)
            return NearbyPlaceViewHolder(binding)
        }
    }
}