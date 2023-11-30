package com.oxymium.realestatemanager.database.agent

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.oxymium.realestatemanager.model.databaseitems.Agent
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