package com.oxymium.realestatemanager.database.agent

import com.oxymium.realestatemanager.model.databaseitems.Agent
import kotlinx.coroutines.flow.Flow

// ----------------
// AgentRepository
// ----------------

class AgentRepository(val agentDao: AgentDao) {

    // GET ALL AGENTS
    // Get allEstates from the DB as Flow
    fun getAllAgents(): Flow<List<Agent>> = agentDao.getAllAgents()

    fun getAgentById(agentId: Long?): Flow<Agent> = agentDao.getAgentById(agentId)

}