package com.oxymium.realestatemanager.features.create.steps

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oxymium.realestatemanager.databinding.ItemMenuStepBinding
import com.oxymium.realestatemanager.model.MenuStep

class MenuStepsViewHolder(val binding: ItemMenuStepBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(menuStep: MenuStep, listener: MenuStepListener) {

        // REQUIRED WITH DATA BINDING
        binding.executePendingBindings()

        // BIND DATA
        binding.menuStep = menuStep

        // Picture
        binding.createNavigatorButtonStep.setImageResource(menuStep.icon)

        // Bind onClickListener
        binding.menuStepListener = listener

        // Remove unwanted buttons

    }

    companion object {

        fun from(parent: ViewGroup): MenuStepsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemMenuStepBinding.inflate(layoutInflater, parent, false)
            return MenuStepsViewHolder(binding)

        }
    }
}