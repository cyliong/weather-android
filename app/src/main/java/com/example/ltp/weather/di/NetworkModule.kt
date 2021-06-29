package com.example.ltp.weather.di

import com.example.ltp.weather.BASE_URL
import com.example.ltp.weather.model.City
import com.example.ltp.weather.model.Weather
import com.example.ltp.weather.network.WeatherApiService
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    fun provideBaseUrl(): String = BASE_URL

    @Provides
    fun provideWeatherApiService(baseUrl: String): WeatherApiService {
        val citiesDeserializer = JsonDeserializer<List<City>> { json, _, _ ->
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

        val weatherDeserializer = JsonDeserializer { json, _, _ ->
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

        return retrofit.create(WeatherApiService::class.java)
    }

}