package com.oxymium.realestatemanager.database.agent

import com.oxymium.realestatemanager.model.databaseitems.Agent
import kotlinx.coroutines.flow.Flow

interface AgentRepository {

    fun getAllAgents(): Flow<List<Agent>>

    fun getAgentById(agentId: Long?): Flow<Agent>

}