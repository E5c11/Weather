package com.demo.weather.weather

import android.util.Log
import com.demo.weather.common.helper.Constant.LOADING
import com.demo.weather.common.helper.Resource
import com.demo.weather.weather.data.exception.WeatherFetchException
import com.demo.weather.weather.data.exception.WeatherNotFoundAtLocationException
import com.demo.weather.weather.helper.HourlyFlow
import kotlinx.coroutines.flow.flow

class WeatherRepository(
    private val dataSource: WeatherDataSource
) {

    fun fetchClosest(lat: Long, lng: Long): HourlyFlow = flow {
        emit(Resource.loading(LOADING))
        try {
            val stations = dataSource.getStations(lat, lng)
            val closestStation = stations.data[0]
            Log.d("myT", "fetchClosest: $closestStation")
            if (closestStation.id != null && closestStation.name?.en != null) {
                val result = dataSource.getHourly(closestStation.id!!, closestStation.name!!.en!!)
                emit(Resource.success(result))
            } else emit(Resource.error(WeatherNotFoundAtLocationException()))
        } catch (e: WeatherFetchException) {
            emit(Resource.error(error = e))
        } catch (e: Exception) {
            emit(Resource.error(WeatherFetchException(error = e)))
        }
    }
}