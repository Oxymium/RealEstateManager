package com.oxymium.realestatemanager.features.create

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.oxymium.realestatemanager.model.databaseitems.Label

// ------------
// LabelAdapter
// ------------

class LabelAdapter(private val labelListener: LabelListener): ListAdapter<Label, LabelViewHolder>(
    LabelDataAdapterListDiff()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabelViewHolder {
        return LabelViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: LabelViewHolder, position: Int) {
        holder.bind(getItem(position), labelListener)
    }

    // For smoother/faster lists comparison, also RecyclerView animation
    private class LabelDataAdapterListDiff : DiffUtil.ItemCallback<Label>() {

        override fun areItemsTheSame(oldItem: Label, newItem: Label): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Label, newItem: Label): Boolean {
            return oldItem.id != newItem.id
        }
    }
}

class LabelListener(val labelClickListener: (label: Label) -> Unit) {
    fun onClickLabel(label: Label) = labelClickListener(label)

}