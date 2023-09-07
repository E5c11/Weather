package com.demo.weather.location

import com.demo.weather.common.helper.Constant.FINDING_LOCATION
import com.demo.weather.common.helper.Resource
import com.demo.weather.common.io.ActionableException
import com.demo.weather.location.helper.LocationFlow
import com.demo.weather.location.io.LocationFetchException
import com.demo.weather.location.io.LocationNotFoundException
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocationRepository @Inject constructor(private val dataSource: LocationDataSource) {
    fun fetch(): LocationFlow = flow {
        emit(Resource.loading(FINDING_LOCATION))
        try {
            dataSource.getCurrentLocation().collect { location ->
                if (location == null) {
                    val lastLocation = checkLastLocation()
                    emit(Resource.success(lastLocation))
                } else emit(Resource.success(location))
            }
        } catch (e: ActionableException) {
            emit(Resource.error(error = e))
        } catch (e: Exception) {
            emit(Resource.error(LocationFetchException(error = e)))
        }
    }

    private suspend fun checkLastLocation(): Location {
        val lastLocation = dataSource.getLastLocation()
        return if (lastLocation.latitude == null || lastLocation.longitude == null) {
            throw LocationNotFoundException()
        } else lastLocation
    }
}