package com.oxymium.realestatemanager.features.create.step_agent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oxymium.realestatemanager.databinding.ItemAgentBinding
import com.oxymium.realestatemanager.model.Agent
import com.oxymium.realestatemanager.utils.AgentListener

// ---------------
// AgentViewHolder
// ---------------

class AgentViewHolder(val binding: ItemAgentBinding):
    RecyclerView.ViewHolder(binding.root) {

    fun bind(agent: Agent, agentListener: AgentListener) {

        // REQUIRED WITH DATA BINDING
        binding.executePendingBindings()

        // BIND DATA
        binding.agent = agent
        binding.agentListener = agentListener

    }

    companion object {

        fun from(parent: ViewGroup): AgentViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemAgentBinding.inflate(layoutInflater, parent, false)
            return AgentViewHolder(binding)

        }
    }

}