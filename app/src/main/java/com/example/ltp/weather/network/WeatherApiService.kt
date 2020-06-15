package com.example.ltp.weather.network

import com.example.ltp.weather.GET_CITIES_REQUEST
import com.example.ltp.weather.GET_WEATHER_REQUEST
import com.example.ltp.weather.model.City
import com.example.ltp.weather.model.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET(GET_CITIES_REQUEST)
    fun getCities(@Query("query") name: String): Call<List<City>>

    @GET(GET_WEATHER_REQUEST)
    fun getWeather(@Query("q") cityName: String): Call<Weather>

}
