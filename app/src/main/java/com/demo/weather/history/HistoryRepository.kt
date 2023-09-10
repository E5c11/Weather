package com.demo.weather.history

import android.util.Log
import com.demo.weather.common.helper.Resource
import com.demo.weather.history.data.exception.HistoryFetchException
import com.demo.weather.history.data.exception.InsertWeatherHistoryException
import com.demo.weather.history.helper.HistoryFlow
import com.demo.weather.history.helper.WeatherLocationsListFlow
import com.demo.weather.history.io.WeatherHistoryDataSource
import com.demo.weather.weather.data.weather.Weather
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class HistoryRepository(private val local: WeatherHistoryDataSource) {
    fun fetchAll(): WeatherLocationsListFlow = flow {
        emit(Resource.loading())
        try {
            local.fetchAll().collect { emit(Resource.success(it)) }
        } catch (e: HistoryFetchException) {
            emit(Resource.error(e))
        } catch (e: Exception) {
            emit(Resource.error(HistoryFetchException(error = e)))
        }
    }

    fun fetchStation(station: String): HistoryFlow = flow {
        emit(Resource.loading())
        try {
            local.fetchByStation(station)?.map { Resource.success(it) }
        } catch (e: Exception) {
            Log.e("WEATHER_HISTORY", "FETCH-ERROR: Could not retrieve station's weather", e)
        }
    }

    suspend fun save(weather: List<Weather>) = try {
        local.insert(weather)
    } catch (e: Exception) {
        Log.e("WEATHER_HISTORY", "CACHE-ERROR: Could not save recent weather", e)
    }
}