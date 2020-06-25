package com.example.ltp.weather.city

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ltp.weather.model.Weather
import com.example.ltp.weather.repository.WeatherRepository
import com.example.ltp.weather.storage.HistoryManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import javax.inject.Inject

class CityViewModel(private val state: SavedStateHandle) : ViewModel() {

    @Inject
    lateinit var historyManager: HistoryManager

    @Inject
    lateinit var weatherRepository: WeatherRepository

    val weatherData = MutableLiveData<Weather>()
    val weatherIconData = MutableLiveData<Bitmap>()

    fun loadWeather(cityName: String) = viewModelScope.launch {
        weatherData.value = weatherRepository.getWeather(cityName)

        // Add city name to the recent list
        historyManager.addCityNameWithCountry(cityName)
    }

    fun loadWeatherImage(imageUrl: String) = viewModelScope.launch {
        weatherIconData.value = getBitmapAsync(imageUrl)
    }

    private suspend fun getBitmapAsync(url: String): Bitmap =
        withContext(Dispatchers.IO) {
            URL(url).openStream().use {
                return@withContext BitmapFactory.decodeStream(it)
            }
        }

}