package com.oxymium.realestatemanager.database

import androidx.sqlite.db.SimpleSQLiteQuery
import com.oxymium.realestatemanager.database.estate.EstateDao
import com.oxymium.realestatemanager.database.estate.EstateRepository
import com.oxymium.realestatemanager.model.databaseitems.Estate
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class EstateRepositoryTest {

    private val estateDao = mockk<EstateDao>()
    private val estateRepository = EstateRepository(estateDao)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAllEstatesTest() = runTest {
        // Given
        val expectedEstates = List(3) { Estate() }

        every { estateDao.getLocalisedEstate() } returns flowOf(expectedEstates)

        // When
        val estates = estateRepository.getAllEstates().first()

        // Then
        assertEquals(expectedEstates, estates)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getSearchedEstatesTest() = runTest {
        // Given
        val query = SimpleSQLiteQuery("FROM ESTATE*")
        val expectedEstates = listOf(Estate(), Estate(), Estate())

        every { estateDao.getSearchedEstates(query) } returns flowOf(expectedEstates)

        // When
        val getSearchedEstates = estateRepository.getSearchedEstates(query).first()

        // Then
        assertEquals(expectedEstates, getSearchedEstates)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getEstateWithIdTest() = runTest {
        // Given
        // Instanced Estate with default constructor will be id = 0L
        val expectedEstate = Estate()
        val expectedEstateId = 0L

        every { estateDao.getEstateWithId(expectedEstateId) } returns flowOf(expectedEstate)

        // When
        val getEstateById = estateRepository.getEstateWithId(expectedEstateId).first()

        // Then
        assertEquals(expectedEstate, getEstateById)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun insertTest() = runTest {

        // Given
        val estate = Estate()
        val expectedEstateId = 10L

        coEvery { estateDao.insert(estate) } returns expectedEstateId

        // When
        estateRepository.insert(estate)

        // Then
        coVerify { estateDao.insert(estate) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteAllTest() = runTest {

        // Given
        coEvery { estateDao.deleteAll() } just Runs

        // When
        estateRepository.deleteAll()

        // Then
        coVerify { estateDao.deleteAll() }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun updateEstateTest() = runTest {

        // Given
        val givenEstate = Estate()

        coEvery { estateDao.update(givenEstate) } just Runs

        // When
        estateRepository.updateEstate(givenEstate)

        // Then
        coVerify { estateDao.update(givenEstate) }


    }

}
