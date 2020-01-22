package com.example.ltp.weather.city

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import com.example.ltp.weather.model.Weather
import com.example.ltp.weather.network.WeatherService
import com.example.ltp.weather.storage.HistoryManager
import kotlinx.coroutines.*
import java.net.URL
import javax.inject.Inject

class CityViewModel @Inject constructor(
    private val historyManager: HistoryManager,
    private val weatherService: WeatherService) {

    private val completableJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + completableJob)

    val weatherData = MutableLiveData<Weather>()
    val weatherIconData = MutableLiveData<Bitmap>()

    fun loadWeather(cityName: String) {
        coroutineScope.launch(Dispatchers.Main) {
            weatherData.value = getWeather(cityName)

            // Add city name to the recent list
            historyManager.addCityNameWithCountry(cityName)
        }
    }

    fun loadWeatherImage(imageUrl: String) {
        coroutineScope.launch(Dispatchers.Main) {
            weatherIconData.value = getBitmapAsync(imageUrl)
        }
    }

    fun onDestroy() {
        coroutineScope.cancel()
    }

    private suspend fun getWeather(cityName: String) =
        withContext(Dispatchers.IO) {
            return@withContext weatherService.getWeather(cityName)
        }

    private suspend fun getBitmapAsync(url: String): Bitmap =
        withContext(Dispatchers.IO) {
            URL(url).openStream().use {
                return@withContext BitmapFactory.decodeStream(it)
            }
        }

}