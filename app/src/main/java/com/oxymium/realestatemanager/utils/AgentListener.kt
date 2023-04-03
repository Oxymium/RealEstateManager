package com.oxymium.realestatemanager.utils

import com.oxymium.realestatemanager.model.Agent

// -------------
// AgentListener
// -------------

class AgentListener(val agentClickListener: (agent: Agent) -> Unit) {

    fun onClickAgent(agent: Agent) = agentClickListener(agent)

}
