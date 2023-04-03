package com.oxymium.realestatemanager.features.estates

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oxymium.realestatemanager.databinding.ItemPictureBinding
import com.oxymium.realestatemanager.model.Picture
import com.oxymium.realestatemanager.utils.PictureListener

// ------------------------
// DetailsPictureViewHolder
// ------------------------

class DetailsPictureViewHolder(val binding: ItemPictureBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(picture: Picture, listener: PictureListener) {

        // REQUIRED WITH DATA BINDING
        binding.executePendingBindings()

        // BIND DATA
        binding.picture = picture

        // Bind onClickListener
        binding.pictureClickListener = listener

        // Remove unwanted buttons
        binding.itemPictureButtonDelete.visibility = GONE
        binding.itemPictureButtonEdit.visibility = GONE

    }

    companion object {

        fun from(parent: ViewGroup): DetailsPictureViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemPictureBinding.inflate(layoutInflater, parent, false)
            return DetailsPictureViewHolder(binding)

        }
    }
}