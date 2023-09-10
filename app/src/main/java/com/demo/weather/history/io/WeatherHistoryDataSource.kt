package com.demo.weather.history.io

import com.demo.weather.history.data.WeatherEntity
import com.demo.weather.weather.data.weather.Weather

interface WeatherHistoryDataSource {
    suspend fun insert(weather: List<Weather>): Long
    suspend fun fetchByStation(): WeatherEntity
    fun fetchAll()
}