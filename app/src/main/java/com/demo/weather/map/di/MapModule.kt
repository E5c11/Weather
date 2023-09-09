package com.demo.weather.map.di

import com.demo.weather.map.usecase.GetNearbyStationsWeatherUseCase
import com.demo.weather.weather.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object MapModule {

    @Provides
    fun providesGetNearbyStationsWeather(repo: WeatherRepository): GetNearbyStationsWeatherUseCase =
        GetNearbyStationsWeatherUseCase(repo)
}