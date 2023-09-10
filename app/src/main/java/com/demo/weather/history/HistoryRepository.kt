package com.demo.weather.history

import android.util.Log
import com.demo.weather.common.helper.Resource
import com.demo.weather.history.helper.HistoryFlow
import com.demo.weather.history.helper.HistoryListFlow
import com.demo.weather.history.io.WeatherHistoryDataSource
import com.demo.weather.weather.data.weather.Weather
import kotlinx.coroutines.flow.flow

class HistoryRepository(private val local: WeatherHistoryDataSource) {
    fun fetchAll(): HistoryListFlow = flow {
        emit(Resource.loading())
        try {

        } catch (e: Exception) {

        }
    }

    fun fetchStation(): HistoryFlow = flow {
        emit(Resource.loading())
        try {

        } catch (e: Exception) {

        }
    }

    suspend fun save(weather: List<Weather>) = try {
        local.insert(weather)
    } catch (e: Exception) {
        Log.e("WEATHER_HISTORY", "CACHE-ERROR: Could not save recent weather", e)
    }
}