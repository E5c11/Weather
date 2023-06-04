package com.demo.weather.location

import com.demo.weather.common.helper.Resource
import com.demo.weather.location.helper.LocationFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocationRepository @Inject constructor(private val dataSource: LocationDataSource) {
    suspend fun fetch(): LocationFlow = flow {
        try {
            val result = dataSource.getCurrentLocation().first()
            emit(Resource.success(result))
        } catch (e: Exception) {
            emit(Resource.error(e))
        }
    }
}