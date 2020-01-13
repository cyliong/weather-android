package com.example.ltp.weather.storage

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class HistoryManagerTest {

    private lateinit var storage: Storage
    private lateinit var historyManager: HistoryManager

    @Before
    fun setup() {
        storage = FakeStorage()
        historyManager = HistoryManager(storage)
    }

    @Test
    fun `Should be able to store city names`() {
        val list = historyManager.getRecentCities()
        assertTrue(list.isEmpty())

        historyManager.addCityNameWithCountry("New York, United States")
        historyManager.addCityNameWithCountry("London, United Kingdom")
        historyManager.addCityNameWithCountry("Tokyo, Japan")

        val newList = historyManager.getRecentCities()
        assertEquals(3, newList.size)
    }

    @Test
    fun `Should not be able to store city names more than the allowed limit`() {
        for (index in 1..historyManager.historySize + 2) {
            historyManager.addCityNameWithCountry("City $index, Country $index")
        }
        val list = historyManager.getRecentCities()
        assertEquals(historyManager.historySize, list.size)
    }

    @Test
    fun `The added city should be on the top of the list`() {
        for (index in 1..3) {
            historyManager.addCityNameWithCountry("City $index, Country $index")
        }
        val list = historyManager.getRecentCities()
        val city = list.first()
        assertEquals("City 3", city.name)
        assertEquals("Country 3", city.country)
    }

}