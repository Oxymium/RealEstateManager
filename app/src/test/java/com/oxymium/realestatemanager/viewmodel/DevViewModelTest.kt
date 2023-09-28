package com.oxymium.realestatemanager.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.oxymium.realestatemanager.database.EstateRepository
import com.oxymium.realestatemanager.database.PictureRepository
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class DevViewModelTest{

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val estateRepository = mockk<EstateRepository>()
    private val pictureRepository = mockk<PictureRepository>()
    private val devViewModel = DevViewModel(estateRepository, pictureRepository)

    @Test
    fun updateNotificationIdTest(){
        // Given
        val id = 10L

        // Using JAVA reflection to access private method
        val devViewModel = DevViewModel(estateRepository, pictureRepository)
        val updateNotificationId = devViewModel.javaClass.getDeclaredMethod("updateNotificationId", Long::class.java)
        updateNotificationId.isAccessible = true

        // When
        updateNotificationId.invoke(devViewModel, id)
        val result = devViewModel.notificationId.value

        // Then
        assertEquals(id, result)
    }

    @Test
    fun updateGivenNumberTest(){
        // Given
        val value = 1000

        // When
        devViewModel.updateGivenNumber(value)
        val result = devViewModel.givenNumber.value

        // Then
        assertEquals(result, value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun onCLickAddButtonTest() = runTest {
    }
}