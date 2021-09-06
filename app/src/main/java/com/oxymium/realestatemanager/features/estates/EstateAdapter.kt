package com.oxymium.realestatemanager.features.estates

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.oxymium.realestatemanager.model.Estate
import com.oxymium.realestatemanager.utils.EstateListener

// ----------------------------
// EstateAdapter (RecyclerView)
// ----------------------------

class EstateAdapter(private val estateListener: EstateListener): ListAdapter<Estate, EstateViewHolder>(EstateDataAdapterListDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstateViewHolder {
        return EstateViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: EstateViewHolder, position: Int) {
        holder.bind(getItem(position), estateListener)

    }

    // For smoother/faster lists comparison, also RecyclerView animation
    private class EstateDataAdapterListDiff : DiffUtil.ItemCallback<Estate>() {

        override fun areItemsTheSame(oldItem: Estate, newItem: Estate): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Estate, newItem: Estate): Boolean {
            return oldItem == newItem
        }
    }

}
