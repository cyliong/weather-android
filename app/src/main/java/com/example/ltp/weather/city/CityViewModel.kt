package com.example.ltp.weather.city

import android.graphics.BitmapFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.ltp.weather.repository.WeatherRepository
import com.example.ltp.weather.storage.HistoryManager
import kotlinx.coroutines.Dispatchers
import java.net.URL
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

    fun getWeatherImage(imageUrl: String) = liveData(Dispatchers.IO) {
        val bitmap = URL(imageUrl).openStream().use {
            BitmapFactory.decodeStream(it)
        }
        emit(bitmap)
    }

}