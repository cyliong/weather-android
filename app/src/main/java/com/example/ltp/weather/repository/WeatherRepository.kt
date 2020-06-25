package com.example.ltp.weather.repository

import com.example.ltp.weather.model.City
import com.example.ltp.weather.model.Weather
import com.example.ltp.weather.network.WeatherApiService
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(baseUrl: String) {

    private val weatherApiService: WeatherApiService

    init {
        val citiesDeserializer = JsonDeserializer<List<City>> {
                json, _, _ ->
            val results = json.asJsonObject
                .get("search_api").asJsonObject
                .get("result").asJsonArray
            val cities = mutableListOf<City>()
            for (result in results) {
                val areaNames = result.asJsonObject.get("areaName").asJsonArray
                val cityName = areaNames[0].asJsonObject.get("value").asString
                val countryNames = result.asJsonObject.get("country").asJsonArray
                val countryName = countryNames[0].asJsonObject.get("value").asString
                cities.add(City(cityName, countryName))
            }
            return@JsonDeserializer cities
        }

        val weatherDeserializer = JsonDeserializer<Weather> {
                json, _, _ ->
            val currentCondition = json.asJsonObject
                .get("data").asJsonObject
                .get("current_condition").asJsonArray[0].asJsonObject

            val iconUrl = currentCondition
                .get("weatherIconUrl").asJsonArray[0].asJsonObject
                .get("value").asString

            val humidity = currentCondition.get("humidity").asDouble

            val description = currentCondition
                .get("weatherDesc").asJsonArray[0].asJsonObject
                .get("value").asString

            val temperature = currentCondition.get("temp_C").asInt

            return@JsonDeserializer Weather(iconUrl, humidity, description, temperature)
        }

        val gson = GsonBuilder()
            .registerTypeAdapter(List::class.java, citiesDeserializer)
            .registerTypeAdapter(Weather::class.java, weatherDeserializer)
            .create()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(baseUrl)
            .build()

        weatherApiService = retrofit.create(WeatherApiService::class.java)
    }

    suspend fun getCities(name: String) = weatherApiService.getCities(name)

    suspend fun getWeather(cityName: String) = weatherApiService.getWeather(cityName)

}