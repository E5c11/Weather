package com.demo.weather.weather

import android.location.Location
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
            val result = dataSource.getFiveDayWeather(lat, lng)
            emit(Resource.success(result))
        } catch (e: Exception) {
            emit(Resource.error(e))
        }

    }

}