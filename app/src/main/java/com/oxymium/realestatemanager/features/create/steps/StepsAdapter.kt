package com.oxymium.realestatemanager.features.create.steps

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.oxymium.realestatemanager.model.Step


class StepListener(val stepClickListener: (step: Step) -> Unit) {
    fun onClickStep(step: Step) = stepClickListener(step)

}
class StepsAdapter(private val stepListener: StepListener): ListAdapter<Step, StepsViewHolder>(
    StepDataAdapterListDiff()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepsViewHolder {
        return StepsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: StepsViewHolder, position: Int) {
        holder.bind(getItem(position), stepListener)
    }

    // For smoother/faster lists comparison, also RecyclerView animation
    private class StepDataAdapterListDiff : DiffUtil.ItemCallback<Step>() {

        override fun areItemsTheSame(oldItem: Step, newItem: Step): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Step, newItem: Step): Boolean {
            return oldItem.id == newItem.id
        }
    }
}