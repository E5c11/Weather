package com.demo.weather.history.io

import com.demo.weather.history.data.exception.HistoryFetchException
import com.demo.weather.history.data.exception.InsertWeatherHistoryException
import com.demo.weather.history.data.toEntity
import com.demo.weather.history.data.toList
import com.demo.weather.weather.data.weather.Weather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalWeatherHistoryDataSource(private val dao: WeatherHistoryDao): WeatherHistoryDataSource {
    override suspend fun insert(weather: List<Weather>): Long = try {
        dao.insert(weather.toEntity())
    } catch (e: Exception) {
        throw InsertWeatherHistoryException(error = e)
    }

    override suspend fun fetchByStation(station: String): List<Weather>? = dao.fetchByStation(station)?.weather

    override fun fetchAll(): Flow<List<List<Weather>>> = try {
        dao.fetchAll().map { entities ->
            entities.map { it.toList() }
        }
    } catch (e: Exception) {
        throw HistoryFetchException(error = e)
    }
}