package com.demo.weather.history.io

import com.demo.weather.history.data.WeatherEntity

interface WeatherHistoryDataSource {
    suspend fun insert(): Long
    suspend fun fetchByStation(): WeatherEntity
    fun fetchAll()
}