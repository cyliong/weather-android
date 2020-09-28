package com.example.ltp.weather.city

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.ltp.weather.repository.WeatherRepository
import com.example.ltp.weather.storage.HistoryManager
import javax.inject.Inject

class CityViewModel(private val state: SavedStateHandle) : ViewModel() {

    @Inject
    lateinit var historyManager: HistoryManager

    @Inject
    lateinit var weatherRepository: WeatherRepository

    fun getWeather(cityName: String) = liveData {
        val weather = weatherRepository.getWeather(cityName)

        // Add city name to the recent list
        historyManager.addCityNameWithCountry(cityName)

        emit(weather)
    }

}