package com.example.ltp.weather.city

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import com.example.ltp.weather.BASE_URL
import com.example.ltp.weather.model.Weather
import com.example.ltp.weather.network.WeatherService
import kotlinx.coroutines.*
import java.net.URL

class CityViewModel {

    private val completableJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + completableJob)

    val weatherData = MutableLiveData<Weather>()
    val weatherIconData = MutableLiveData<Bitmap>()

    fun loadWeather(cityName: String) {
        coroutineScope.launch(Dispatchers.Main) {
            weatherData.value = getWeather(cityName)
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
            return@withContext WeatherService(BASE_URL).getWeather(cityName)
        }

    private suspend fun getBitmapAsync(url: String): Bitmap =
        withContext(Dispatchers.IO) {
            URL(url).openStream().use {
                return@withContext BitmapFactory.decodeStream(it)
            }
        }

}