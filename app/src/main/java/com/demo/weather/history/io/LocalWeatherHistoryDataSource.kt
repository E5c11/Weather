package com.demo.weather.history.io

import com.demo.weather.history.data.WeatherEntity

class LocalWeatherHistoryDataSource(private val dao: WeatherHistoryDao): WeatherHistoryDataSource {
    override suspend fun insert(): Long {
        TODO("Not yet implemented")
    }

    override suspend fun fetchByStation(): WeatherEntity {
        TODO("Not yet implemented")
    }

    override fun fetchAll() {
        TODO("Not yet implemented")
    }
}