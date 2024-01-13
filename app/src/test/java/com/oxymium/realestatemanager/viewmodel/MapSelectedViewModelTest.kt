package com.oxymium.realestatemanager.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.oxymium.realestatemanager.database.estate.EstateRepository
import com.oxymium.realestatemanager.model.CategoryHelper
import com.oxymium.realestatemanager.model.mock.generateOneRandomEstate
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

class MapSelectedViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val estateRepository = mockk<EstateRepository>()

    private lateinit var mapSelectedViewModel: MapSelectedViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        mapSelectedViewModel = MapSelectedViewModel(estateRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun updateSelectedEstateTest() {
        // GIVEN
        val givenEstate = generateOneRandomEstate()
        // WHEN
        mapSelectedViewModel.updateSelectedEstate(givenEstate)
        val updatedSelectedEstate = mapSelectedViewModel.selectedEstate.value
        // THEN
        assertEquals(givenEstate, updatedSelectedEstate)
    }

    @Test
    fun updateIconHelper() {
        // GIVEN
        val givenCategoryHelper = CategoryHelper.CategoryRooms
        // WHEN
        mapSelectedViewModel.updateIconHelper(givenCategoryHelper)
        val updatedIconHelper = mapSelectedViewModel.iconHelper.value
        // THEN
        assertEquals(givenCategoryHelper, updatedIconHelper)
    }

    @Test
    fun onClickIconsTest() {
        // GIVEN
        val givenCategoryHelper = CategoryHelper.CategoryBathrooms // should correspond to 4
        // WHEN
        mapSelectedViewModel.onClickIcons(4)
        val updatedIconHelper = mapSelectedViewModel.iconHelper.value
        // THEN
        assertEquals(givenCategoryHelper, updatedIconHelper)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun onClickDetailsButtonTest() = runTest {
        // GIVEN
        val givenBoolean = true
        // WHEN
        mapSelectedViewModel.onClickDetailsButton()
        // THEN
        val job = launch { mapSelectedViewModel.isDetailsButtonClicked.collect {
            assertEquals(givenBoolean, it)
        }}
        job.cancel()
    }

}