package com.oxymium.realestatemanager.database

import com.oxymium.realestatemanager.database.agent.AgentDao
import com.oxymium.realestatemanager.database.agent.AgentRoomImpl
import com.oxymium.realestatemanager.model.databaseitems.Agent
import com.oxymium.realestatemanager.model.mock.generateRandomAgent
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class AgentRepositoryTest {

    // Instantiate Mockk objects
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetAllAgents() = runTest {
        // Given
        val agentDao = mockk<AgentDao>()
        val expectedAgents = listOf(
            generateRandomAgent()
        )
        val agentRepository = AgentRoomImpl(agentDao)

        every { agentDao.getAllAgents() } returns flowOf(expectedAgents)

        // When
        val agents = agentRepository.getAllAgents().first()

        // Then
        assertEquals(expectedAgents, agents)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAgentByIdTest() = runTest {
        // Given
        val agentDao = mockk<AgentDao>()
        val agentRepository = AgentRoomImpl(agentDao)
        val expectedAgent = Agent(false,"firstName", "lastName", "06.00.00.00.00", "proton@mock.com", "agency_1", id = 10L)
        val givenAgentId = 10L

        every { agentDao.getAgentById(givenAgentId) } returns flowOf(expectedAgent)

        // When
        val agent = agentRepository.getAgentById(givenAgentId).first()

        // Then
        assertEquals(expectedAgent, agent)
    }

}