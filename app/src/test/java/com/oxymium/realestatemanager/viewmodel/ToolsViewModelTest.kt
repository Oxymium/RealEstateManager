package com.oxymium.realestatemanager.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.oxymium.realestatemanager.TOOL_MENU_STEPS
import junit.framework.TestCase.assertEquals
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


class ToolsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var toolsViewModel: ToolsViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        toolsViewModel = ToolsViewModel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun updateCurrentToolMenuStepTest() {
        // GIVEN
        val givenToolMenuStep = TOOL_MENU_STEPS.shuffled()[0]
        // WHEN
        toolsViewModel.updateCurrentToolMenuStep(givenToolMenuStep)
        val updatedToolMenuStep = toolsViewModel.currentToolMenuStep.value
        // THEN
        assertEquals(updatedToolMenuStep, givenToolMenuStep)
    }

    @Test
    fun updateToolMenuStepsTest() {
        // GIVEN
        val givenToolMenuSteps = TOOL_MENU_STEPS
        // WHEN
        toolsViewModel.updateToolMenuSteps(givenToolMenuSteps)
        val updatedToolMenuSteps = toolsViewModel.toolMenuSteps.value
        // THEN
        assertEquals(updatedToolMenuSteps, givenToolMenuSteps)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun updateToolMenuStepTest() = runTest {
        // GIVEN
        val givenToolMenuStep = TOOL_MENU_STEPS.shuffled()[0]
        // WHEN
        toolsViewModel.updateToolMenuStep(givenToolMenuStep)
        // THEN
        val job = launch {
            toolsViewModel.toolMenuStep.collect{
                assertEquals(givenToolMenuStep, it)
            }
        }
        job.cancel()
    }

}