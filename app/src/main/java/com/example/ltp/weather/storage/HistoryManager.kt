package com.example.ltp.weather.storage

import com.example.ltp.weather.model.City

private const val HISTORY_KEY = "recent_cities"
private const val HISTORY_SEPARATOR = "|"

/**
 * Manager a list of recently viewed cities
 */
class HistoryManager(private val storage: Storage) {

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
        val storedCityNames = getRecentCityNamesWithCountry()

        val newCityNames = mutableListOf(name)
        newCityNames.addAll(storedCityNames)

        // If the city has been stored previously, remove it from the list
        val index = storedCityNames.indexOf(name)
        if (index != -1) {
            newCityNames.removeAt(index)
        }

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