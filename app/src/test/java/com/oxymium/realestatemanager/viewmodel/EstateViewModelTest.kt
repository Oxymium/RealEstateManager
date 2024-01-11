package com.oxymium.realestatemanager.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.oxymium.realestatemanager.database.agent.AgentRepository
import com.oxymium.realestatemanager.database.estate.EstateRepository
import com.oxymium.realestatemanager.database.picture.PictureRepository
import com.oxymium.realestatemanager.model.Search
import com.oxymium.realestatemanager.model.databaseitems.Estate
import com.oxymium.realestatemanager.model.mock.generateOneRandomEstate
import com.oxymium.realestatemanager.model.mock.generateRandomAgent
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull

class EstateViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val agentRepository = mockk<AgentRepository>()
    private val estateRepository = mockk<EstateRepository>()
    private val pictureRepository = mockk<PictureRepository>()

    private lateinit var estateViewModel: EstateViewModel
    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        coEvery { estateRepository.getAllEstates() } returns flow { emptyList<Estate>() }
        estateViewModel = EstateViewModel(agentRepository, estateRepository, pictureRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun toggleIsTabletTest() {
        // GIVEN
        val givenValue = true
        // WHEN
        estateViewModel.toggleIsTablet(givenValue)
        val updatedIsTablet = estateViewModel.isTablet.value
        // THEN
        assertEquals(givenValue, updatedIsTablet)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun updateShouldNavigateToDetailsFragment() = runTest {
        // GIVEN
        val givenBoolean = true
        // WHEN
        estateViewModel.updateShouldNavigateToDetailsFragment(givenBoolean)
        // THEN
        val job = launch { estateViewModel.shouldNavigateToDetailsFragment.collect {
            assertEquals(givenBoolean, it)
        }}

        job.cancel()
    }

    @Test
    fun updateSelectedEstateIdTest() {
        // GIVEN
        val givenEstateId = 10L
        // WHEN
        estateViewModel.updateSelectedEstateId(givenEstateId)
        val updatedSelectedEstateId = estateViewModel.selectedEstateId.value
        // THEN
        assertEquals(givenEstateId, updatedSelectedEstateId)
    }

    @Test
    fun updateAgentIdTest() {
        // GIVEN
        val givenAgentId = 10L
        // WHEN
        estateViewModel.updateAgentId(givenAgentId)
        val updatedAgentId = estateViewModel.agentId.value
        // THEN
        assertEquals(givenAgentId, updatedAgentId)
    }

    @Test
    fun getAgentByIdTest() = runTest {
        // GIVEN
        val givenAgent = generateRandomAgent()
        // WHEN
        estateViewModel.getAgentById(givenAgent.id)
        // THEN
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun updateSearchQueryTest() = runTest {
        // GIVEN
        val givenSearchQuery = Search()
        // WHEN
        estateViewModel.updateSearchQuery(givenSearchQuery)
        val job = launch { estateViewModel.searchQuery.collect {
            assertEquals(givenSearchQuery, it)
        }}
        job.cancel()
    }

    @Test
    fun updateToggleSearchButtonTest() {
        // GIVEN
        val givenBoolean = true
        // WHEN
        estateViewModel.updateToggleSearchButton(givenBoolean)
        val updatedToggleSearchButton = estateViewModel.toggleSearchButton.value
        // THEN
        assertEquals(givenBoolean, updatedToggleSearchButton)
    }

    @Test
    fun onClickSearchButtonTest() {
        // GIVEN
        val givenBoolean = false
        // WHEN
        estateViewModel.onClickSearchButton()
        val updatedToggleSearchButton = estateViewModel.toggleSearchButton.value
        // THEN
        assertNotEquals(givenBoolean, updatedToggleSearchButton) // verifies it never goes to false
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun updateEstateToEditTest() = runTest {
        // GIVEN
        val givenEstate = generateOneRandomEstate()
        // WHEN
        estateViewModel.updateEstateToEdit(givenEstate)
        // THEN
        val job = launch { estateViewModel.estateToEdit.collect {
            assertEquals(givenEstate, it)
        } }

        job.cancel()
    }

    @Test
    fun onClickSellButtonTest() {
        // GIVEN
        val givenBoolean = false
        // WHEN
        estateViewModel.onClickEditButton()
        val updatedWasSellButtonClicked = estateViewModel.wasSellButtonClicked.value
        // THEN
        assertEquals(givenBoolean, updatedWasSellButtonClicked)
    }

    @Test
    fun onClickDatabaseRefreshButtonTest() {
        // GIVEN
        val givenBoolean = true
        // WHEN
        estateViewModel.onClickDatabaseRefreshButton()
        val updatedDatabaseRefreshed = estateViewModel.databaseRefreshed.value
        // THEN
        assertEquals(givenBoolean, updatedDatabaseRefreshed)
    }

    @Test
    fun updateStartAddedDateTest() {
        // GIVEN
        val givenDate = 123456789L
        // WHEN
        estateViewModel.updateStartAddedDate(givenDate)
        val updatedStartAddedDate = estateViewModel.startAddedDate.value
        // THEN
        assertEquals(givenDate, updatedStartAddedDate)
    }

    @Test
    fun updateEndAddedDateTest() {
        // GIVEN
        val givenDate = 123456789L
        // WHEN
        estateViewModel.updateEndAddedDate(givenDate)
        val updatedEndAddedDate = estateViewModel.endAddedDate.value
        // THEN
        assertEquals(givenDate, updatedEndAddedDate)
    }

    @Test
    fun updateStartSoldDateTest() {
        // GIVEN
        val givenDate = 123456789L
        // WHEN
        estateViewModel.updateStartSoldDate(givenDate)
        val updatedStartSoldDate = estateViewModel.startSoldDate.value
        // THEN
        assertEquals(givenDate, updatedStartSoldDate)
    }

    @Test
    fun updateEndSoldDateTest() {
        // GIVEN
        val givenDate = 123456789L
        // WHEN
        estateViewModel.updateEndSoldDate(givenDate)
        val updatedEndSoldDate = estateViewModel.endSoldDate.value
        // THEN
        assertEquals(givenDate, updatedEndSoldDate)
    }

    @Test
    fun onClickDateButtonTest() {
        // GIVEN
        val givenValue = 1
        // WHEN
        estateViewModel.onClickDateButton(givenValue)
        val updatedDateType = estateViewModel.dateType.value
        // THEN
        assertEquals(givenValue, updatedDateType)
    }

    @Test
    fun updateSoldDateVisibilityTest() {
        // GIVEN
        val givenBoolean = true
        // WHEN
        estateViewModel.updateSoldDateVisibility(givenBoolean)
        val updatedSoldDateVisibility = estateViewModel.soldDateVisibility.value
        // THEN
        assertEquals(givenBoolean, updatedSoldDateVisibility)
    }

    @Test
    fun resetDateValuesTest() {
        // GIVEN
        val givenFrom = 1
        // WHEN
        estateViewModel.resetDateValues(givenFrom)
        val updatedStartAddedDate = estateViewModel.startAddedDate.value
        val updatedEndAddedDate = estateViewModel.endAddedDate.value
        // THEN
        assertNull(updatedStartAddedDate)
        assertNull(updatedEndAddedDate)
    }
}