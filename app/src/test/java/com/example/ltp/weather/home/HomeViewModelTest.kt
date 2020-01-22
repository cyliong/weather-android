package com.example.ltp.weather.home

import com.example.ltp.weather.network.WeatherService
import com.example.ltp.weather.storage.HistoryManager
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @Mock
    private lateinit var mockHistoryManager: HistoryManager

    @Mock
    private lateinit var mockWeatherService: WeatherService

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        viewModel = HomeViewModel(mockHistoryManager, mockWeatherService)
    }

    @Test
    fun `getRecentCities should call HistoryManager's getRecentCities method`() {
        viewModel.getRecentCities()

        verify(mockHistoryManager).getRecentCities()
    }

}