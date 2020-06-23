package com.example.ltp.weather.city

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.ltp.weather.model.Weather
import com.example.ltp.weather.repository.WeatherRepository
import com.example.ltp.weather.storage.HistoryManager
import kotlinx.coroutines.*
import java.net.URL
import javax.inject.Inject

class CityViewModel(private val state: SavedStateHandle) : ViewModel() {

    @Inject
    lateinit var historyManager: HistoryManager

    @Inject
    lateinit var weatherRepository: WeatherRepository

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

    override fun onCleared() {
        coroutineScope.cancel()
        super.onCleared()
    }

    private suspend fun getWeather(cityName: String) =
        withContext(Dispatchers.IO) {
            return@withContext weatherRepository.getWeather(cityName)
        }

    private suspend fun getBitmapAsync(url: String): Bitmap =
        withContext(Dispatchers.IO) {
            URL(url).openStream().use {
                return@withContext BitmapFactory.decodeStream(it)
            }
        }

}