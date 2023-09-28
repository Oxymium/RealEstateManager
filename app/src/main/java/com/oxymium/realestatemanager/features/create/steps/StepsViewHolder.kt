package com.oxymium.realestatemanager.features.create.steps

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oxymium.realestatemanager.databinding.ItemStepBinding
import com.oxymium.realestatemanager.model.Step

class StepsViewHolder(val binding: ItemStepBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(step: Step, listener: StepListener) {

        // REQUIRED WITH DATA BINDING
        binding.executePendingBindings()

        // BIND DATA
        binding.step = step

        // Picture
        binding.createNavigatorButtonStep.setImageResource(step.picture)

        // Bind onClickListener
        binding.stepListener = listener

        // Remove unwanted buttons

    }

    companion object {

        fun from(parent: ViewGroup): StepsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemStepBinding.inflate(layoutInflater, parent, false)
            return StepsViewHolder(binding)

        }
    }
}