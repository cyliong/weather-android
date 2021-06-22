package com.example.ltp.weather.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ltp.weather.model.City
import com.example.ltp.weather.repository.WeatherRepository
import com.example.ltp.weather.storage.HistoryManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val state: SavedStateHandle,
    private val historyManager: HistoryManager,
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    val cityData = MutableLiveData<List<City>>()

    fun loadCities(name: String) = viewModelScope.launch {
        cityData.value = weatherRepository.getCities(name)
    }

    fun getRecentCities() = historyManager.getRecentCities()

}