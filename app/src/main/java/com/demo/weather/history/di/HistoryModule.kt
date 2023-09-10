package com.demo.weather.history.di

import com.demo.weather.history.HistoryRepository
import com.demo.weather.history.io.LocalWeatherHistoryDataSource
import com.demo.weather.history.io.WeatherHistoryDao
import com.demo.weather.history.io.WeatherHistoryDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object HistoryModule {

    @Provides
    fun providesHistoryRepository(dataSource: WeatherHistoryDataSource) =
        HistoryRepository(dataSource)

    @Provides
    fun providesLocalHistoryDataSource(dao: WeatherHistoryDao): WeatherHistoryDataSource =
        LocalWeatherHistoryDataSource(dao)

}