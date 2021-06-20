package com.example.ltp.weather.di

import com.example.ltp.weather.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    fun provideBaseUrl(): String = BASE_URL

}