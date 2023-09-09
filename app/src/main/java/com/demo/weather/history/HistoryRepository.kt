package com.demo.weather.history

import com.demo.weather.common.helper.Resource
import com.demo.weather.history.helper.HistoryFlow
import com.demo.weather.history.helper.HistoryListFlow
import com.demo.weather.history.io.WeatherHistoryDataSource
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
}