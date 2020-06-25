package com.example.ltp.weather.network

import com.example.ltp.weather.GET_CITIES_REQUEST
import com.example.ltp.weather.GET_WEATHER_REQUEST
import com.example.ltp.weather.model.City
import com.example.ltp.weather.model.Weather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET(GET_CITIES_REQUEST)
    suspend fun getCities(@Query("query") name: String): List<City>

    @GET(GET_WEATHER_REQUEST)
    suspend fun getWeather(@Query("q") cityName: String): Weather

}
