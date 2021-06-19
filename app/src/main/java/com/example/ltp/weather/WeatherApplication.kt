package com.example.ltp.weather

import android.app.Application
import com.example.ltp.weather.di.AppComponent
import com.example.ltp.weather.di.DaggerAppComponent
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class WeatherApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

}