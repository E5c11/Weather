package com.demo.weather.weather

import android.location.Location
import android.util.Log
import com.demo.weather.common.helper.Constant.LOADING
import com.demo.weather.common.helper.Resource
import com.demo.weather.weather.helper.FiveDayFlow
import kotlinx.coroutines.flow.flow

class WeatherRepository(
    private val dataSource: WeatherDataSource
) {

    fun fetch(lat: Long, lng: Long): FiveDayFlow = flow {
        emit(Resource.loading(LOADING))
        try {
            Log.d("myT", "fetch: before")
            val result = dataSource.getFiveDayWeather(lat, lng)
            Log.d("myT", "fetch: $result")
            emit(Resource.success(result))
        } catch (e: Exception) {
            emit(Resource.error(e))
        }

    }

}