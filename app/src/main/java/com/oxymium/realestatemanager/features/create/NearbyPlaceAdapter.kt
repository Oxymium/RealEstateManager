package com.oxymium.realestatemanager.features.create

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.oxymium.realestatemanager.model.NearbyPlace

// ------------------
// NearbyPlaceAdapter
// ------------------

class NearbyPlaceAdapter(private val nearbyPLaceListener: NearbyPlaceListener): ListAdapter<NearbyPlace, NearbyPlaceViewHolder>(
    NearbyPlaceDataAdapterListDiff()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearbyPlaceViewHolder {
        return NearbyPlaceViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NearbyPlaceViewHolder, position: Int) {
        holder.bind(getItem(position), nearbyPLaceListener)
    }

    // For smoother/faster lists comparison, also RecyclerView animation
    private class NearbyPlaceDataAdapterListDiff : DiffUtil.ItemCallback<NearbyPlace>() {

        override fun areItemsTheSame(oldItem: NearbyPlace, newItem: NearbyPlace): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: NearbyPlace, newItem: NearbyPlace): Boolean {
            return oldItem.id != newItem.id
        }
    }
}

class NearbyPlaceListener(val nearbyPlaceClickListener: (nearbyPlace: NearbyPlace) -> Unit) {
    fun onClickNearbyPlace(nearbyPlace: NearbyPlace) = nearbyPlaceClickListener(nearbyPlace)

}