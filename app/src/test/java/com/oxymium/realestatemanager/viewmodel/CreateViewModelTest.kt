package com.oxymium.realestatemanager.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.oxymium.realestatemanager.CREATE_MENU_STEPS
import com.oxymium.realestatemanager.database.agent.AgentRepository
import com.oxymium.realestatemanager.database.estate.EstateRepository
import com.oxymium.realestatemanager.database.picture.PictureRepository
import com.oxymium.realestatemanager.model.EstateField
import com.oxymium.realestatemanager.model.MenuStep
import com.oxymium.realestatemanager.model.ReachedSide
import com.oxymium.realestatemanager.model.databaseitems.Estate
import com.oxymium.realestatemanager.model.databaseitems.Picture
import com.oxymium.realestatemanager.model.mock.NEARBY_PLACES
import com.oxymium.realestatemanager.model.mock.generateOneRandomEstate
import com.oxymium.realestatemanager.model.mock.generateOneRandomPicture
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertNotEquals

class CreateViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val agentRepository = mockk<AgentRepository>()
    private val estateRepository = mockk<EstateRepository>()
    private val pictureRepository = mockk<PictureRepository>()

    private lateinit var createViewModel: CreateViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        coEvery { agentRepository.getAllAgents() } returns flowOf(emptyList())
        createViewModel = CreateViewModel(agentRepository, estateRepository, pictureRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun updateEstateTest() {
        // GIVEN
        val givenEstate = Estate()
        // WHEN
        createViewModel.updateEstate(givenEstate)
        val estate = createViewModel.estate.value
        // THEN
        assertEquals(givenEstate, estate)
    }

    @Test
    fun updateEstateFieldTest() {
        // GIVEN
        val givenPrice = 120000
        // WHEN
        createViewModel.updateEstateField(EstateField.Price(givenPrice))
        val updatedEstatePrice = createViewModel.estate.value?.price
        // THEN
        assertEquals(updatedEstatePrice, givenPrice)
    }

    @Test
    fun updateCurrentCreateMenuStepTest() {
        // GIVEN
        val givenMenuStep = MenuStep(icon = 0, id = 1, title = "Overview")
        // WHEN
        createViewModel.updateCurrentCreateMenuStep(givenMenuStep)
        val updatedCurrentCreateMenuStep = createViewModel.currentCreateMenuStep.value
        // THEN
        assertEquals(givenMenuStep, updatedCurrentCreateMenuStep)
    }

    @Test
    fun updateCreateMenuStepsTest() {
        // GIVEN
        val givenMenuSteps = CREATE_MENU_STEPS
        // WHEN
        createViewModel.updateCreateMenuSteps(givenMenuSteps)
        val updatedCreateMenuSteps = createViewModel.createMenuSteps.value
        // THEN
        assertEquals(givenMenuSteps, updatedCreateMenuSteps)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun updateCreateMenuStepTest() = runTest {
        // GIVEN
        val givenMenuStep = CREATE_MENU_STEPS.shuffled().take(1)[0]
        // WHEN
        createViewModel.updateCreateMenuStep(givenMenuStep)
        // THEN
        advanceTimeBy(1000)
        val result = launch { createViewModel.createMenuStep
            .take(1)
            .collect {
                assertEquals(givenMenuStep, it)
                println(givenMenuStep)
                println(it)
            }
        }
        result.cancel()
    }

    @Test
    fun updateReachedStepSideTest() {
        // GIVEN
        val givenReachedStepSide = ReachedSide.LeftSide
        // WHEN
        createViewModel.updateReachedStepSide(givenReachedStepSide)
        val updatedReachedSide = createViewModel.reachedStepSide.value
        // THEN
        assertEquals(givenReachedStepSide, updatedReachedSide)
    }

    @Test
    fun updateReachedAgentSideTest() {
        // GIVEN
        val givenReachedAgentSide = ReachedSide.RightSide
        // WHEN
        createViewModel.updateReachedAgentSide(givenReachedAgentSide)
        val updatedReachedAgentSide = createViewModel.reachedAgentSide.value
        // THEN
        assertEquals(givenReachedAgentSide, updatedReachedAgentSide)
    }

    @Test
    fun updateReachedTypeSideTest() {
        // GIVEN
        val givenReachedTypeSide = ReachedSide.TopSide
        // WHEN
        createViewModel.updateReachedTypeSide(givenReachedTypeSide)
        val updatedReachedTypeSide = createViewModel.reachedTypeSide.value
        // THEN
        assertEquals(givenReachedTypeSide, updatedReachedTypeSide)
    }

    @Test
    fun updateReachedNearbyPlacesSideTest() {
        // GIVEN
        val givenReachedNearbyPlacesSide = ReachedSide.BottomSide
        // WHEN
        createViewModel.updateReachedNearbyPlacesSide(givenReachedNearbyPlacesSide)
        val updatedReachedNearbyPlacesSide = createViewModel.reachedNearbyPlacesSide.value
        // THEN
        assertEquals(givenReachedNearbyPlacesSide, updatedReachedNearbyPlacesSide)
    }

    @Test
    fun updateNearbyPlaceStringValueTest() {
        // GIVEN
        val givenNearbyPlaceStringValue = "Nearby places"
        // WHEN
        createViewModel.updateNearbyPlaceStringValue(givenNearbyPlaceStringValue)
        val updatedNearbyPlaceStringValue = createViewModel.nearbyPlaceStringValue.value
        // THEN
        assertEquals(givenNearbyPlaceStringValue, updatedNearbyPlaceStringValue)
    }

    @Test
    fun updateNearbyPlacesTest() {
        // GIVEN
        val givenNearbyPlaces = NEARBY_PLACES
        // WHEN
        createViewModel.updateNearbyPlaces(givenNearbyPlaces)
        val updatedNearbyPlaces = createViewModel.nearbyPlaces.value
        // THEN
        assertEquals(givenNearbyPlaces, updatedNearbyPlaces)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getPicturesForGivenEstateIdTest() = runTest {
        // GIVEN
        val givenEstateId = 10L
        coEvery { pictureRepository.getAllPicturesForGivenEstateId(givenEstateId) } returns flowOf(listOf(
            Picture("", "", givenEstateId)))
        // WHEN
        createViewModel.getPicturesForGivenEstateId(givenEstateId)
        val secondaryPicturesResult = createViewModel.secondaryPictures.value
        // THEN
        assertEquals(givenEstateId, secondaryPicturesResult?.get(0)?.estate_id)
    }

    @Test
    fun updateSecondaryPicturesPreviewTest() {
        // GIVEN
        val givenPicturePathPreview = "Path"
        // WHEN
        createViewModel.updateSecondaryPicturePreview(givenPicturePathPreview)
        val updatedSecondaryPicturePreview = createViewModel.secondaryPicturePreview.value
        // THEN
        assertEquals(givenPicturePathPreview, updatedSecondaryPicturePreview)
    }

    @Test
    fun addPictureToSecondaryPicturesTest() {
        // GIVEN
        val givenPicture = generateOneRandomPicture()
        // WHEN
        createViewModel.addPictureToSecondaryPictures(givenPicture)
        val secondaryPictures = createViewModel.secondaryPictures.value
        // THEN
        assertEquals(givenPicture, secondaryPictures?.get(0))
    }

    @Test
    fun deletePictureFromSecondaryListTest() {
        val createViewModel = mockk<CreateViewModel>(relaxed = true)
        // GIVEN
        val givenPicture = generateOneRandomPicture()
        val givenPictureList = listOf(givenPicture)
        every { createViewModel.secondaryPictures.value } returns givenPictureList
        // WHEN
        createViewModel.deletePictureFromSecondaryList(givenPicture)
        val secondaryPictures = createViewModel.secondaryPictures.value
        // THEN
        assertEquals(givenPicture, secondaryPictures?.get(0))
    }

    @Test
    fun updateCommentFromSecondaryPicturesTest() {
        val createViewModel = mockk<CreateViewModel>(relaxed = true)
        // GIVEN
        val givenOldPicture = generateOneRandomPicture()
        val givenNewPicture = givenOldPicture.copy(comment = "New comment")
        every { createViewModel.secondaryPictures.value } returns listOf(givenNewPicture)
        // WHEN
        createViewModel.updateCommentFromSecondaryPictures(givenOldPicture, givenNewPicture)
        val secondaryPictures = createViewModel.secondaryPictures.value
        // THEN
        assertEquals(givenNewPicture, secondaryPictures?.get(0))

    }

    @Test
    fun updateEnableReverseGeoCodingTest() {
        // GIVEN
        val givenBoolean = true
        // WHEN
        createViewModel.updateEnableReverseGeoCoding(givenBoolean)
        val updatedEnableReverseGeoCoding = createViewModel.enableReverseGeoCoding.value
        // THEN
        assertEquals(givenBoolean, updatedEnableReverseGeoCoding)
    }

    @Test
    fun onClickAddMainPictureButtonTest() {
        // GIVEN
        val expectedValue = 1
        // WHEN
        createViewModel.onClickAddMainPictureButton()
        val updatedPictureActivityType = createViewModel.pictureActivityType.value
        // THEN
        assertEquals(expectedValue, updatedPictureActivityType)
    }

    @Test
    fun onClickDeleteMainPictureButtonTest() {
        // GIVEN
        val expectedValue: String? = null
        // WHEN
        createViewModel.onClickDeleteMainPictureButton()
        val updatedEstate = createViewModel.estate.value?.mainPicturePath
        // THEN
        assertEquals(expectedValue, updatedEstate)
    }

    @Test
    fun onClickAddSecondaryPictureButtonTest() {
        // GIVEN
        val expectedValue = 2
        // WHEN
        createViewModel.onClickAddSecondaryPictureButton()
        val updatedPictureActivityType = createViewModel.pictureActivityType.value
        // THEN
        assertEquals(expectedValue, updatedPictureActivityType)
    }

    @Test
    fun fillSecondaryPicturesTest() {
        // GIVEN
        val givenNullList: List<Picture>? = null
        // WHEN
        createViewModel.fillSecondaryPictures()
        val updatedSecondaryPictures = createViewModel.secondaryPictures.value
        // THEN
        assertNotEquals(givenNullList, updatedSecondaryPictures)
    }

    @Test
    fun fillEstateFieldsTest() {
        // GIVEN
        val givenEstate = generateOneRandomEstate()
        // WHEN
        createViewModel.fillEstateFields(givenEstate)
        val updatedEstate = createViewModel.estate.value?.price
        // THEN
        assertEquals(givenEstate.price, updatedEstate)
    }

}