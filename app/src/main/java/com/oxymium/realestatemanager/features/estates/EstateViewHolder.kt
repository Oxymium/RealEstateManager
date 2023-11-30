package com.oxymium.realestatemanager.features.estates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oxymium.realestatemanager.databinding.ItemEstateBinding
import com.oxymium.realestatemanager.model.databaseitems.Estate

// ------------------------------
// EstateViewHolder (RecyclerView)
// ------------------------------

class EstateViewHolder(val binding: ItemEstateBinding):
    RecyclerView.ViewHolder(binding.root) {

    fun bind(estate: Estate, estateListener: EstateListener) {

        // REQUIRED WITH DATA BINDING
        binding.executePendingBindings()

        // BIND DATA
        binding.estate = estate
        binding.estateClickListener = estateListener

    }

    companion object {

        fun from(parent: ViewGroup): EstateViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemEstateBinding.inflate(layoutInflater, parent, false)
            return EstateViewHolder(binding)

        }
    }
}

