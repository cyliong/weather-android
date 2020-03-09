package com.example.ltp.weather.repository

import com.example.ltp.weather.BASE_URL
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class WeatherRepositoryTest {

    private lateinit var weatherRepository: WeatherRepository

    @Before
    fun setup() {
        weatherRepository = WeatherRepository(BASE_URL)
    }

    @Test
    fun getCitiesByName() {
        val cities = weatherRepository.getCities("toa payoh")
        Assert.assertTrue(cities.isNotEmpty())
    }

    @Test
    fun getWeatherByName() {
        val weather = weatherRepository.getWeather("Toa Payoh, Singapore")
        Assert.assertNotNull(weather)
    }

}