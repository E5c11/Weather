package com.demo.weather.history.io

import com.demo.weather.history.data.WeatherEntity
import com.demo.weather.history.data.exception.InsertWeatherHistoryException
import com.demo.weather.history.data.toEntity
import com.demo.weather.weather.data.weather.Weather

class LocalWeatherHistoryDataSource(private val dao: WeatherHistoryDao): WeatherHistoryDataSource {
    override suspend fun insert(weather: List<Weather>): Long = try {
        dao.insert(weather.toEntity())
    } catch (e: Exception) {
        throw InsertWeatherHistoryException(error = e)
    }

    override suspend fun fetchByStation(): WeatherEntity {
        TODO("Not yet implemented")
    }

    override fun fetchAll() {
        TODO("Not yet implemented")
    }
}