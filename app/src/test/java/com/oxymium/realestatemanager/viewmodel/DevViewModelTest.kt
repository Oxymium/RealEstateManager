package com.oxymium.realestatemanager.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.oxymium.realestatemanager.database.estate.EstateRepository
import com.oxymium.realestatemanager.database.picture.PictureRepository
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

class DevViewModelTest{

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val estateRepository = mockk<EstateRepository>()
    private val pictureRepository = mockk<PictureRepository>()
    private val devViewModel = DevViewModel(estateRepository, pictureRepository)

    @Test
    fun updateGivenNumberTest(){
        // GIVEN
        val value = 1000
        // WHEN
        devViewModel.updateGivenNumber(value)
        val result = devViewModel.givenNumber.value
        // THEN
        assertEquals(result, value)
    }

}