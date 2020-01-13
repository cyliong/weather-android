package com.example.ltp.weather

import android.app.Application
import com.example.ltp.weather.storage.HistoryManager
import com.example.ltp.weather.storage.SharedPreferencesStorage

class WeatherApplication : Application() {

    val historyManager by lazy {
        HistoryManager(SharedPreferencesStorage(this))
    }

}