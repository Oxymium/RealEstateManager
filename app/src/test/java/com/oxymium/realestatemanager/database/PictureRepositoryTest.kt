package com.oxymium.realestatemanager.database

import com.oxymium.realestatemanager.database.picture.PictureDao
import com.oxymium.realestatemanager.database.picture.PictureRoomImpl
import com.oxymium.realestatemanager.model.databaseitems.Picture
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class PictureRepositoryTest {

    private val pictureDao = mockk<PictureDao>()
    private val pictureRepository = PictureRoomImpl(pictureDao)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAllPicturesForGivenEstateIdTest() = runTest {
        // Given
        val pictureDao = mockk<PictureDao>()
        val expectedEstateId = 2L
        val expectedPictures = listOf(
            Picture("", "", expectedEstateId, 1L),
            Picture("", "", expectedEstateId, 2L)
        )

        every { pictureDao.getAllPicturesForGivenEstateId(expectedEstateId) } returns flowOf(expectedPictures)

        // When
        val picturesForGivenEstate = pictureRepository.getAllPicturesForGivenEstateId(expectedEstateId).first()

        // Then
        assertEquals(expectedPictures, picturesForGivenEstate)

    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun insertTest() = runTest {

        // Given
        val picture = Picture("", "", 1L, 10L)

        coEvery { pictureDao.insertPicture(picture) } just runs

        // When
        pictureRepository.insertPicture(picture)

        // Then
        coVerify { pictureDao.insertPicture(picture) }
    }

}