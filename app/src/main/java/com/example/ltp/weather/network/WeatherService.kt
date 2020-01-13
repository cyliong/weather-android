package com.example.ltp.weather.network

import android.util.Log
import com.example.ltp.weather.API_KEY
import com.example.ltp.weather.model.City
import com.example.ltp.weather.model.Weather
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

private const val TAG = "WeatherService"

class WeatherService(private val baseUrl: String) {

    fun getCities(name: String): List<City> {
        val cityList = mutableListOf<City>()
        try {
            val jsonObject = getServiceResponse(
                "search",
                "query=${name}")

            val searchNode = jsonObject.getJSONObject("search_api")
            val resultNodes = searchNode.getJSONArray("result")
            for (index in 0 until resultNodes.length()) {
                val resultNode = resultNodes.getJSONObject(index)

                val areaNodes = resultNode.getJSONArray("areaName")
                val cityName = areaNodes.getJSONObject(0).getString("value")

                val countryNodes = resultNode.getJSONArray("country")
                val countryName = countryNodes.getJSONObject(0).getString("value")

                val city = City(cityName, countryName)

                Log.d(TAG, "${city.name}, ${city.country}")

                cityList.add(city)
            }

        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
        return cityList
    }

    fun getWeather(cityName: String): Weather? {
        try {
            val jsonObject = getServiceResponse(
                "weather",
                "q=${cityName}&num_of_days=1")

            val dataNode = jsonObject.getJSONObject("data")
            val currentConditionNode = dataNode
                .getJSONArray("current_condition")
                .getJSONObject(0)

            val iconUrl = currentConditionNode
                .getJSONArray("weatherIconUrl")
                .getJSONObject(0)
                .getString("value")

            val humidity = currentConditionNode.getDouble("humidity")

            val description = currentConditionNode
                .getJSONArray("weatherDesc")
                .getJSONObject(0)
                .getString("value")

            val temperature = currentConditionNode.getInt("temp_C")

            Log.d(TAG, iconUrl)
            Log.d(TAG, humidity.toString())
            Log.d(TAG, description)
            Log.d(TAG, temperature.toString())

            return Weather(iconUrl, humidity, description, temperature)

        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
        return null
    }

    private fun getServiceResponse(service: String, query: String): JSONObject {
        val builder = StringBuilder()

        val urlString = "${baseUrl}${service}.ashx?${encodeQuery(query)}&format=json&key=${API_KEY}"
        Log.v(TAG, "URL: $urlString")

        val url = URL(urlString)
        val connection = url.openConnection() as HttpURLConnection
        var data: Int = connection.inputStream.read()
        while (data != -1) {
            builder.append(data.toChar())
            data = connection.inputStream.read()
        }
        val json = builder.toString()
        Log.v(TAG, json)
        return JSONObject(json)
    }

    private fun encodeQuery(query: String) = query.replace(" ", "+")

}