package com.demo.weather.history.io

import com.demo.weather.weather.data.weather.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherHistoryDataSource {
    suspend fun insert(weather: List<Weather>): Long
    suspend fun fetchByStation(station: String): List<Weather>?
    fun fetchAll(): Flow<List<List<Weather>>>
}