package com.example.ltp.weather.model

data class Weather(
    val iconUrl: String,
    val humidity: Double,
    val description: String,
    val temperature: Int
)