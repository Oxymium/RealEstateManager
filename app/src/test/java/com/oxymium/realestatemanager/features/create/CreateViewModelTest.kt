package com.oxymium.realestatemanager.features.create

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.oxymium.realestatemanager.database.agent.AgentRepository
import com.oxymium.realestatemanager.database.estate.EstateRepository
import com.oxymium.realestatemanager.database.picture.PictureRepository
import com.oxymium.realestatemanager.model.databaseitems.Estate
import io.mockk.mockk
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test

class CreateViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    // Mock dependencies
    private val agentRepository = mockk<AgentRepository>()
    private val estateRepository = mockk<EstateRepository>()
    private val pictureRepository = mockk<PictureRepository>()

    private val createViewModel = CreateViewModel(agentRepository, estateRepository, pictureRepository)

    @Test
    fun updateEstateTest(){
        // GIVEN
        val givenEstate = Estate()
        // WHEN
        createViewModel.updateEstate(givenEstate)
        val estate = createViewModel.estate.value
        // THEN
        TestCase.assertEquals(givenEstate, estate)
    }

    @Test
    fun updateEditedEstate(){
        // GIVEN
        val givenEditedEstate = Estate()
        // WHEN
        createViewModel.updateEditedEstate(givenEditedEstate)
        val editedEstate = createViewModel.editedEstate.value
        // THEN
        TestCase.assertEquals(givenEditedEstate, editedEstate)
    }

    @Test
    fun updateAvailabilityTest(){
        // GIVEN & WHEN
        createViewModel.updateAvailability(true)
        // THEN
        TestCase.assertEquals(true, createViewModel.availability.value)
    }

    @Test
    fun updateAvailabilitySinceTest(){
        // GIVEN
        val anyGivenDate = (1..10000000).random().toLong()
        // WHEN
        createViewModel.updateAvailabilitySince(anyGivenDate)
        val availableSince = createViewModel.availableSince.value
        // THEN
        TestCase.assertEquals(anyGivenDate, availableSince)
    }

    @Test
    fun updatePurchaseDate(){
        // GIVEN
        val anyGivenDate = (1..10000000).random().toLong()
        // WHEN
        createViewModel.updatePurchaseDate(anyGivenDate)
        val date = createViewModel.purchaseDate.value
        // THEN
        TestCase.assertEquals(anyGivenDate, date)
    }

}