package com.example.ltp.weather.di

import com.example.ltp.weather.storage.SharedPreferencesStorage
import com.example.ltp.weather.storage.Storage
import dagger.Binds
import dagger.Module

@Module
abstract class StorageModule {

    @Binds
    abstract fun provideStorage(storage: SharedPreferencesStorage): Storage

}