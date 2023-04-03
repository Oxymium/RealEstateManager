package com.oxymium.realestatemanager.features.create

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.oxymium.realestatemanager.database.AgentRepository
import com.oxymium.realestatemanager.database.EstateRepository
import com.oxymium.realestatemanager.database.PictureRepository
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
    fun updateAgentTest() {
        // GIVEN
        val givenAgent = "Maxfield Stein"
        // WHEN
        createViewModel.updateAgent(givenAgent)
        val agent = createViewModel.agent.value
        // THEN
        TestCase.assertEquals(givenAgent, agent)
    }

    @Test
    fun updatePriceTest() {
        // GIVEN
        val anyGivenPrice = (0 ..Int.MAX_VALUE).random()
        // WHEN
        createViewModel.updatePrice(anyGivenPrice)
        val price = createViewModel.price.value
        // THEN
        TestCase.assertEquals(anyGivenPrice, price)
    }

    @Test
    fun updateSurfaceTest(){
        // GIVEN
        val anyGivenSurface = (0 ..Int.MAX_VALUE).random()
        // WHEN
        createViewModel.updateSurface(anyGivenSurface)
        val surface = createViewModel.surface.value
        // THEN
        TestCase.assertEquals(anyGivenSurface, surface)
    }

    @Test
    fun updateRoomsTest(){
        // GIVEN
        val anyGivenRooms = (0 ..Int.MAX_VALUE).random()
        // WHEN
        createViewModel.updateRooms(anyGivenRooms)
        val rooms = createViewModel.rooms.value
        // THEN
        TestCase.assertEquals(anyGivenRooms, rooms)
    }

    @Test
    fun updateBedroomsTest(){
        // GIVEN
        val anyGivenBedrooms = (0 ..Int.MAX_VALUE).random()
        // WHEN
        createViewModel.updateBedrooms(anyGivenBedrooms)
        val bedrooms = createViewModel.bedrooms.value
        // THEN
        TestCase.assertEquals(anyGivenBedrooms, bedrooms)
    }

    @Test
    fun updateBathroomsTest(){
        // GIVEN
        val anyGivenBathrooms = (0 ..Int.MAX_VALUE).random()
        // WHEN
        createViewModel.updateBathrooms(anyGivenBathrooms)
        val bathrooms = createViewModel.bathrooms.value
        // THEN
        TestCase.assertEquals(anyGivenBathrooms, bathrooms)
    }

    @Test
    fun updateAddressTest(){
        // GIVEN
        val givenAddress = "23 eagle street"
        // WHEN
        createViewModel.updateStreet(givenAddress)
        val address = createViewModel.street.value
        // THEN
        TestCase.assertEquals(givenAddress, address)
    }

    @Test
    fun updateLocationTest(){
        // GIVEN
        val givenLocation = "Paris"
        // WHEN
        createViewModel.updateLocation(givenLocation)
        val address = createViewModel.location.value
        // THEN
        TestCase.assertEquals(givenLocation, address)
    }

    @Test
    fun updateZipcodeTest(){
        // GIVEN
        val givenZipCode = "00000"
        // WHEN
        createViewModel.updateZipCode(givenZipCode)
        val zipCode = createViewModel.zipCode.value
        // THEN
        TestCase.assertEquals(givenZipCode, zipCode)
    }

    @Test
    fun updateHighSpeedInternetTest(){
       // GIVEN & WHEN
        createViewModel.updateHighSpeedInternet(false)
        // THEN
        TestCase.assertEquals(false, createViewModel.highSpeedInternet.value)
    }

    @Test
    fun updateEnergyClassTest(){
        // GIVEN
        val givenEnergyClass = "A"
        // WHEN
        createViewModel.updateStreet(givenEnergyClass)
        val energyClass = createViewModel.street.value
        // THEN
        TestCase.assertEquals(givenEnergyClass, energyClass)
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

    @Test
    fun updateDescription(){
        // GIVEN
        val givenDescription = "Lorem Ipsum"
        // WHEN
        createViewModel.updateDescription(givenDescription)
        val description = createViewModel.description.value
        // THEN
        TestCase.assertEquals(givenDescription, description)
    }

    @Test
    fun updateEstateType(){
        // GIVEN
        val givenType = "Bungallow"
        // WHEN
        createViewModel.updateEstateType(givenType)
        val type = createViewModel.type.value
        // THEN
        TestCase.assertEquals(givenType, type)
    }

}