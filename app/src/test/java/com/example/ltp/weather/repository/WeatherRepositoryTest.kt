package com.example.ltp.weather.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ltp.weather.network.WeatherApiService
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@Config(application = HiltTestApplication::class)
class WeatherRepositoryTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var weatherRepository: WeatherRepository

    @Inject
    lateinit var weatherApiService: WeatherApiService

    @Before
    fun setup() {
        hiltRule.inject()
        weatherRepository = WeatherRepository(weatherApiService)
    }

    @Test
    fun getCitiesByName() = runBlocking {
        val cities = weatherRepository.getCities("toa payoh")
        Assert.assertTrue(cities.isNotEmpty())
    }

    @Test
    fun getWeatherByName() = runBlocking {
        val weather = weatherRepository.getWeather("Toa Payoh, Singapore")
        Assert.assertNotNull(weather)
    }

}