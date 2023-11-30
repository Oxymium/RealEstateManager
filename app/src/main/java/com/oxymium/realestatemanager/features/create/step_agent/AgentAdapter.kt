package com.oxymium.realestatemanager.features.create.step_agent

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.oxymium.realestatemanager.model.databaseitems.Agent

// ------------
// AgentAdapter
// ------------
class AgentAdapter(private val agentListener: AgentListener): ListAdapter<Agent, AgentViewHolder>(
    AgentDataAdapterListDiff()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgentViewHolder {
        return AgentViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AgentViewHolder, position: Int) {
        holder.bind(getItem(position), agentListener)
    }

    // For smoother/faster lists comparison, also RecyclerView animation
    private class AgentDataAdapterListDiff : DiffUtil.ItemCallback<Agent>() {

        override fun areItemsTheSame(oldItem: Agent, newItem: Agent): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Agent, newItem: Agent): Boolean {
            return oldItem.isSelected != newItem.isSelected
        }
    }
}

// -------------
// AgentListener
// -------------
class AgentListener(val agentClickListener: (agent: Agent) -> Unit) {
    fun onClickAgent(agent: Agent) = agentClickListener(agent)

}