package com.oxymium.realestatemanager.features.estates

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.oxymium.realestatemanager.model.databaseitems.Picture

// ---------------------
// DetailsPictureAdapter
// ---------------------

class DetailsPictureAdapter(private val pictureListener: PictureListener): ListAdapter<Picture, DetailsPictureViewHolder>(PictureDataAdapterListDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsPictureViewHolder {
        return DetailsPictureViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DetailsPictureViewHolder, position: Int) {
        holder.bind(getItem(position), pictureListener)
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