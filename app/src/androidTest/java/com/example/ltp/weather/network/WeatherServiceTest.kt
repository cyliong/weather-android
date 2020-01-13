package com.example.ltp.weather.network

import com.example.ltp.weather.BASE_URL
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class WeatherServiceTest {

    private lateinit var weatherService: WeatherService

    @Before
    fun setup() {
        weatherService = WeatherService(BASE_URL)
    }

    @Test
    fun getCitiesByName() {
        val cities = weatherService.getCities("toa payoh")
        assertTrue(cities.isNotEmpty())
    }

    @Test
    fun getWeatherByName() {
        val weather = weatherService.getWeather("Toa Payoh, Singapore")
        assertNotNull(weather)
    }

}