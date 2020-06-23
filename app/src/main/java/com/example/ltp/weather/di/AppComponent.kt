package com.example.ltp.weather.di

import android.content.Context
import com.example.ltp.weather.city.CityViewModel
import com.example.ltp.weather.home.HomeViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [StorageModule::class, NetworkModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(viewModel: HomeViewModel)
    fun inject(viewModel: CityViewModel)

}