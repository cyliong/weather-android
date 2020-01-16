package com.example.ltp.weather.storage

import com.example.ltp.weather.model.City
import javax.inject.Inject
import javax.inject.Singleton

private const val HISTORY_KEY = "recent_cities"
private const val HISTORY_SEPARATOR = "|"

/**
 * Manager a list of recently viewed cities
 */
@Singleton
class HistoryManager @Inject constructor(private val storage: Storage) {

    val historySize = 10

    fun getRecentCities() : List<City> {
        val storedCityNames = getRecentCityNamesWithCountry()
        return if (storedCityNames.isEmpty()) {
            emptyList()
        } else {
            val cities = mutableListOf<City>()
            for (cityName in storedCityNames) {
                val parts = cityName.split(", ")
                cities.add(City(parts[0], parts[1]))
            }
            return cities
        }
    }

    fun addCityNameWithCountry(name: String) {
        val storedCityNames = getRecentCityNamesWithCountry().toMutableList()

        // If the city has been stored previously, remove it from the list
        val index = storedCityNames.lastIndexOf(name)
        if (index != -1) {
            storedCityNames.removeAt(index)
        }

        val newCityNames = mutableListOf(name)
        newCityNames.addAll(storedCityNames)

        // Remove the last city if exceed the allowed size
        if (newCityNames.size > historySize) {
            newCityNames.removeAt(newCityNames.size - 1)
        }

        val rawCityList = newCityNames.joinToString(HISTORY_SEPARATOR)
        storage.setString(HISTORY_KEY, rawCityList)
    }

    private fun getRecentCityNamesWithCountry() : List<String> {
        val rawCityList = storage.getString(HISTORY_KEY)
        return if (rawCityList.isEmpty()) {
            emptyList()
        } else {
            return rawCityList.split(HISTORY_SEPARATOR)
        }
    }

}