package com.oxymium.realestatemanager.database.agent

import com.oxymium.realestatemanager.model.databaseitems.Agent
import kotlinx.coroutines.flow.Flow

class AgentRoomImpl(private val agentDao: AgentDao): AgentRepository {
    override fun getAllAgents(): Flow<List<Agent>> {
        return agentDao.getAllAgents()
    }

    override fun getAgentById(agentId: Long?): Flow<Agent> {
        return agentDao.getAgentById(agentId)
    }
}