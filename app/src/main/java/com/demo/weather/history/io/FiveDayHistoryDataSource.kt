package com.demo.weather.history.io

interface FiveDayHistoryDataSource {
    suspend fun insert(): Long
    suspend fun fetchById(): FiveDayWeather
    fun fetchAll()
}