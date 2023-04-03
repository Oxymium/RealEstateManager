package com.oxymium.realestatemanager.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

class ToolsViewModelTest(){

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val toolsViewModel = ToolsViewModel()

    @Test
    fun onClickCurrencyCategoryTest(){
        // GIVEN
        val category = toolsViewModel.categoryClicked.value
        // WHEN
        toolsViewModel.onClickCurrencyCategory()
        // THEN
        assertEquals(1, category)
    }

    @Test
    fun onClickLoanCategoryTest(){
        // GIVEN
        val category = toolsViewModel.categoryClicked.value
        // WHEN
        toolsViewModel.onClickLoanCategory()
        // THEN
        assertEquals(2, category)
    }

    @Test
    fun onClickDevyCategoryTest(){
        // GIVEN
        val category = toolsViewModel.categoryClicked.value
        // WHEN
        toolsViewModel.onClickDevCategory()
        // THEN
        assertEquals(3, category)
    }

}