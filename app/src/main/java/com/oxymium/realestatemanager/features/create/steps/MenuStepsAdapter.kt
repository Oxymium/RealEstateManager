package com.oxymium.realestatemanager.features.create.steps

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.oxymium.realestatemanager.model.MenuStep


class MenuStepListener(val menuStepClickListener: (menuStep: MenuStep) -> Unit) {
    fun onClickMenuStepListener(menuStep: MenuStep) = menuStepClickListener(menuStep)

}
class MenuStepsAdapter(private val menuStepListener: MenuStepListener): ListAdapter<MenuStep, MenuStepsViewHolder>(
    MenuStepDataAdapterListDiff()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuStepsViewHolder {
        return MenuStepsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MenuStepsViewHolder, position: Int) {
        holder.bind(getItem(position), menuStepListener)
    }

    // For smoother/faster lists comparison, also RecyclerView animation
    private class MenuStepDataAdapterListDiff : DiffUtil.ItemCallback<MenuStep>() {

        override fun areItemsTheSame(oldItem: MenuStep, newItem: MenuStep): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MenuStep, newItem: MenuStep): Boolean {
            return oldItem.id == newItem.id
        }
    }
}