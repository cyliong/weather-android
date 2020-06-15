package com.example.ltp.weather

const val BASE_URL = "https://api.worldweatheronline.com/premium/v1/"

const val API_KEY = ""

const val GET_CITIES_REQUEST = "search.ashx?format=json&key=$API_KEY"
const val GET_WEATHER_REQUEST = "weather.ashx?num_of_days=1&format=json&key=$API_KEY"