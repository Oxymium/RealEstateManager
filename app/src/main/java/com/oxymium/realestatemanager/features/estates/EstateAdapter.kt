package com.oxymium.realestatemanager.features.estates

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oxymium.realestatemanager.model.Estate
import com.oxymium.realestatemanager.utils.EstateListener

// ----------------------------
// EstateAdapter (RecyclerView)
// ----------------------------

class EstateAdapter(private val context: Context, private var estates: List<Estate>, private val estateListener: EstateListener)
    : RecyclerView.Adapter<EstateViewHolder>(){

    override fun onBindViewHolder(holder: EstateViewHolder, position: Int) {
        val estate: Estate = estates[position]
        holder.bind(estate, estateListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstateViewHolder {
        return EstateViewHolder.from(parent)
    }

    override fun getItemCount(): Int = estates.size

    fun updateData(estates: List<Estate>) {
        this.estates = estates
        notifyDataSetChanged()
    }

}
