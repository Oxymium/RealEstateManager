package com.oxymium.realestatemanager.database

import androidx.room.*
import com.oxymium.realestatemanager.model.Agent
import kotlinx.coroutines.flow.Flow

// ---------
// AgentDao
// ---------
@Dao
interface AgentDao {

    // CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(agent: Agent)

    // GET
    @Query("SELECT * FROM agent ")
    fun getAllAgents(): Flow<List<Agent>>

    // GET
    @Query("SELECT * FROM agent WHERE id = :agentId")
    fun getAgentById(agentId: Long?): Flow<Agent>

}