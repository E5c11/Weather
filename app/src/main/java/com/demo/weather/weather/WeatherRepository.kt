package com.demo.weather.weather

import android.util.Log
import com.demo.weather.common.helper.Constant.LOADING
import com.demo.weather.common.helper.Resource
import com.demo.weather.weather.helper.FiveDayFlow
import com.demo.weather.weather.io.WeatherFetchException
import kotlinx.coroutines.flow.flow

class WeatherRepository(
    private val dataSource: WeatherDataSource
) {

    fun fetch(lat: Long, lng: Long): FiveDayFlow = flow {
        emit(Resource.loading(LOADING))
        try {
            Log.d("myT", "fetch: ")
            val result = dataSource.getFiveDayWeather(lat, lng)
            emit(Resource.success(result))
        } catch (e: WeatherFetchException) {
            emit(Resource.error(error = e))
        } catch (e: Exception) {
            emit(Resource.error(WeatherFetchException(error = e)))
        }
    }

}