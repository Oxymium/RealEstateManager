package com.oxymium.realestatemanager.database

import com.oxymium.realestatemanager.model.Agent
import kotlinx.coroutines.flow.Flow

// ----------------
// AgentRepository
// ----------------

class AgentRepository(val agentDao: AgentDao) {

    // GET ALL AGENTS
    // Get allEstates from the DB as Flow
    val allAgents: Flow<List<Agent>> = agentDao.getAllAgents()

    fun getAgentById(agentId: Long?): Flow<Agent> = agentDao.getAgentById(agentId)

}