package com.example.ltp.weather.storage

interface Storage {
    fun getString(key: String): String
    fun setString(key: String, value: String)
}