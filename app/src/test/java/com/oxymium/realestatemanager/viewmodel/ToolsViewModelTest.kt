package com.oxymium.realestatemanager.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.oxymium.realestatemanager.R
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test


class ToolsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val toolsViewModel = ToolsViewModel()

    @Test
    fun updateSelectedToolTest(){
        // Given
        val value = 1
        val expectedFragmentReference = R.id.currencyFragment

        // When
        toolsViewModel.updateSelectedTool(value)
        val selectedTool = toolsViewModel.selectedTool.value

        // Then
        assertEquals(expectedFragmentReference, selectedTool)

    }

    @Test
    fun currentToolTest(){
        // Given
        val value = 100

        // When
        toolsViewModel.updateCurrentTool(value)
        val currentTool = toolsViewModel.currentTool.value

        // Then
        assertEquals(value, currentTool)
    }

}