package com.demo.weather.location

import android.util.Log
import com.demo.weather.common.helper.Constant.FINDING_LOCATION
import com.demo.weather.common.helper.Resource
import com.demo.weather.common.io.ActionableException
import com.demo.weather.location.helper.LocationFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocationRepository @Inject constructor(private val dataSource: LocationDataSource) {
    fun fetch(): LocationFlow = flow {
        emit(Resource.loading(FINDING_LOCATION))
        try {
            dataSource.getCurrentLocation().collect {
                emit(Resource.success(it))
            }

        } catch (e: ActionableException) {
            emit(Resource.error(error = e))
        } catch (e: Exception) {
            emit(Resource.error(ActionableException(error = e)))
        }
    }
}