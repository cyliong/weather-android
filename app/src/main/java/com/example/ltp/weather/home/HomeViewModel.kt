package com.example.ltp.weather.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ltp.weather.model.City
import com.example.ltp.weather.repository.WeatherRepository
import com.example.ltp.weather.storage.HistoryManager
import kotlinx.coroutines.*
import javax.inject.Inject

class HomeViewModel : ViewModel() {

    @Inject
    lateinit var historyManager: HistoryManager

    @Inject
    lateinit var weatherRepository: WeatherRepository

    private val completableJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + completableJob)

    val cityData = MutableLiveData<List<City>>()

    fun loadCities(name: String) {
        coroutineScope.launch(Dispatchers.Main) {
            cityData.value = getCities(name)
        }
    }

    fun getRecentCities() = historyManager.getRecentCities()

    override fun onCleared() {
        coroutineScope.cancel()
        super.onCleared()
    }

    private suspend fun getCities(name: String) =
        withContext(Dispatchers.IO) {
            return@withContext weatherRepository.getCities(name)
        }

}