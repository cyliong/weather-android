package com.example.ltp.weather.repository

import com.example.ltp.weather.network.WeatherApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(private val weatherApiService: WeatherApiService) {

    suspend fun getCities(name: String) = weatherApiService.getCities(name)

    suspend fun getWeather(cityName: String) = weatherApiService.getWeather(cityName)

}