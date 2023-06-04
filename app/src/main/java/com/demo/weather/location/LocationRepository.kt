package com.demo.weather.location

import android.util.Log
import com.demo.weather.common.helper.Constant.FINDING_LOCATION
import com.demo.weather.common.helper.Resource
import com.demo.weather.location.helper.LocationFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocationRepository @Inject constructor(private val dataSource: LocationDataSource) {
    fun fetch(): LocationFlow = flow {
        emit(Resource.loading(FINDING_LOCATION))
        try {
            val result = dataSource.getCurrentLocation().collect{
                Log.d("myT", "fetch: $it")
                emit(Resource.success(it))
            }

        } catch (e: Exception) {
            emit(Resource.error(e))
        }
    }
}