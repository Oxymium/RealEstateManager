package com.oxymium.realestatemanager.features.estates

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.oxymium.realestatemanager.model.databaseitems.Picture

// ----------------------------
// PictureAdapter (RecyclerView)
// ----------------------------

class CreatePictureAdapter(private val pictureListener: PictureListener, private val pictureDeleteListener: PictureDeleteListener, private val pictureCommentListener: PictureCommentListener): ListAdapter<Picture, CreatePictureViewHolder>(PictureDataAdapterListDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreatePictureViewHolder {
        return CreatePictureViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CreatePictureViewHolder, position: Int) {
        holder.bind(getItem(position), pictureListener, pictureDeleteListener, pictureCommentListener)
    }

    // For smoother/faster lists comparison, also RecyclerView animation
    private class PictureDataAdapterListDiff : DiffUtil.ItemCallback<Picture>() {

        override fun areItemsTheSame(oldItem: Picture, newItem: Picture): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Picture, newItem: Picture): Boolean {
            return oldItem.comment == newItem.comment
        }
    }
}