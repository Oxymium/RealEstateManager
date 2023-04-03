package com.oxymium.realestatemanager.features.create.step_one

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oxymium.realestatemanager.databinding.ItemLabelBinding
import com.oxymium.realestatemanager.model.Label
import com.oxymium.realestatemanager.utils.LabelListener

// ---------------
// LabelViewHolder
// ---------------

class LabelViewHolder(val binding: ItemLabelBinding):
    RecyclerView.ViewHolder(binding.root) {

    fun bind(label: Label, labelListener: LabelListener) {

        // REQUIRED WITH DATA BINDING
        binding.executePendingBindings()

        // BIND DATA
        binding.label = label
        binding.labelListener = labelListener

    }

    companion object {

        fun from(parent: ViewGroup): LabelViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemLabelBinding.inflate(layoutInflater, parent, false)
            return LabelViewHolder(binding)
        }
    }
}