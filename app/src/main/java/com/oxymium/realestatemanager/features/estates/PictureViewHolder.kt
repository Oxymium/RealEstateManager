package com.oxymium.realestatemanager.features.estates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oxymium.realestatemanager.databinding.ItemPictureBinding
import com.oxymium.realestatemanager.model.Picture
import com.oxymium.realestatemanager.utils.PictureListener

// -------------------------------
// PictureViewHolder (RecyclerView)
// -------------------------------

class PictureViewHolder(val binding: ItemPictureBinding):
    RecyclerView.ViewHolder(binding.root) {

    fun bind(picture: Picture, pictureListener: PictureListener) {

        // REQUIRED WITH DATA BINDING
        binding.executePendingBindings()

        // BIND DATA
        binding.picture = picture

        // BIND picture description
        binding.itemEstateDescription.text = picture.description

    }

    companion object {

        fun from(parent: ViewGroup): PictureViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemPictureBinding.inflate(layoutInflater, parent, false)
            return PictureViewHolder(binding)

        }

    }

}
