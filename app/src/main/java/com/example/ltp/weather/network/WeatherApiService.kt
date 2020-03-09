package com.example.ltp.weather.network

import com.example.ltp.weather.API_KEY
import com.example.ltp.weather.model.City
import com.example.ltp.weather.model.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("search.ashx?format=json&key=$API_KEY")
    fun getCities(@Query("query") name: String): Call<List<City>>

    @GET("weather.ashx?num_of_days=1&format=json&key=$API_KEY")
    fun getWeather(@Query("q") cityName: String): Call<Weather>

}
