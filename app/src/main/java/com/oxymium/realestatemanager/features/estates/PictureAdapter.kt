package com.oxymium.realestatemanager.features.estates

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oxymium.realestatemanager.model.Picture
import com.oxymium.realestatemanager.utils.PictureListener

// ----------------------------
// PictureAdapter (RecyclerView)
// ----------------------------

class PictureAdapter(private val context: Context, private var pictures: List<Picture>, private val pictureListener: PictureListener)
    : RecyclerView.Adapter<PictureViewHolder>(){

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        val picture: Picture = pictures[position]
        holder.bind(picture, pictureListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        return PictureViewHolder.from(parent)
    }

    override fun getItemCount(): Int = pictures.size

    fun updateData(pictures: List<Picture>) {
        this.pictures = pictures
        notifyDataSetChanged()
    }

}