package com.oxymium.realestatemanager.features.estates

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oxymium.realestatemanager.databinding.ItemEstateBinding
import com.oxymium.realestatemanager.model.Estate
import com.oxymium.realestatemanager.utils.EstateListener

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

        // Tint Estate according to availability (25% RED transparency)
        if (binding.estate?.wasSold == true){
            binding.itemEstatePicture.setColorFilter(Color.parseColor("#40E91E63"))
            // !! Else statement required otherwise recycled items receive all same aforementioned color
        }else{
            binding.itemEstatePicture.clearColorFilter()
        }

        // Different colors for different energy scores
        when (binding.estate?.energy) {
            "A+", "A" -> binding.itemEstateEnergy.setTextColor(Color.parseColor("#4CAF50"))
            "B" -> binding.itemEstateEnergy.setTextColor(Color.parseColor("#FFEB3B"))
            "C" -> binding.itemEstateEnergy.setTextColor(Color.parseColor("#FF9800"))
            "D" -> binding.itemEstateEnergy.setTextColor(Color.parseColor("#E91E63"))
        }

    }

    companion object {

        fun from(parent: ViewGroup): EstateViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemEstateBinding.inflate(layoutInflater, parent, false)
            return EstateViewHolder(binding)

        }

    }

}

