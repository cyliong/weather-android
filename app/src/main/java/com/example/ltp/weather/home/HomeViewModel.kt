package com.example.ltp.weather.home

import androidx.lifecycle.MutableLiveData
import com.example.ltp.weather.model.City
import com.example.ltp.weather.network.WeatherService
import com.example.ltp.weather.storage.HistoryManager
import kotlinx.coroutines.*
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val historyManager: HistoryManager,
    private val weatherService: WeatherService)  {

    private val completableJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + completableJob)

    val cityData = MutableLiveData<List<City>>()

    fun loadCities(name: String) {
        coroutineScope.launch(Dispatchers.Main) {
            cityData.value = getCities(name)
        }
    }

    fun getRecentCities() = historyManager.getRecentCities()

    fun onDestroy() {
        coroutineScope.cancel()
    }

    private suspend fun getCities(name: String) =
        withContext(Dispatchers.IO) {
            return@withContext weatherService.getCities(name)
        }

}