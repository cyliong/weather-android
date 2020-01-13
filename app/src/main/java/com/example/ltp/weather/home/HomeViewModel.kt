package com.example.ltp.weather.home

import androidx.lifecycle.MutableLiveData
import com.example.ltp.weather.BASE_URL
import com.example.ltp.weather.model.City
import com.example.ltp.weather.network.WeatherService
import kotlinx.coroutines.*

class HomeViewModel {

    private val completableJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + completableJob)

    val cityData = MutableLiveData<List<City>>()

    fun loadCities(name: String) {
        coroutineScope.launch(Dispatchers.Main) {
            cityData.value = getCities(name)
        }
    }

    fun onDestroy() {
        coroutineScope.cancel()
    }

    private suspend fun getCities(name: String) =
        withContext(Dispatchers.IO) {
            return@withContext WeatherService(BASE_URL).getCities(name)
        }

}