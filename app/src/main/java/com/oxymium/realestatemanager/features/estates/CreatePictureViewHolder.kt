package com.oxymium.realestatemanager.features.estates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oxymium.realestatemanager.databinding.ItemPictureBinding
import com.oxymium.realestatemanager.model.databaseitems.Picture

// -------------------------------
// PictureViewHolder (RecyclerView)
// -------------------------------

class CreatePictureViewHolder(val binding: ItemPictureBinding):
    RecyclerView.ViewHolder(binding.root) {

    fun bind(picture: Picture, pictureListener: PictureListener, pictureDeleteListener: PictureDeleteListener, pictureCommentListener: PictureCommentListener) {

        // REQUIRED WITH DATA BINDING
        binding.executePendingBindings()

        // BIND DATA
        binding.picture = picture

        binding.pictureClickListener = pictureListener
        binding.pictureClickDeleteListener = pictureDeleteListener
        binding.pictureClickCommentListener = pictureCommentListener

        // BIND picture description
        binding.itemEstateDescription.text = picture.comment

    }

    companion object {

        fun from(parent: ViewGroup): CreatePictureViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemPictureBinding.inflate(layoutInflater, parent, false)
            return CreatePictureViewHolder(binding)

        }

    }

}
