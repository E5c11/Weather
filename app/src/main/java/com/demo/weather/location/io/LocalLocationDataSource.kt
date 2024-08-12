package com.demo.weather.location.io

import android.annotation.SuppressLint
import android.util.Log
import com.demo.weather.common.helper.DispatcherProvider
import com.demo.weather.location.service.LocationService
import com.demo.weather.location.data.Location
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

const val LOCATION_TIMEOUT = 30000L

class LocalLocationDataSource @Inject constructor(
    private var locationService: LocationService,
    private val dispatcher: DispatcherProvider
): LocationDataSource {

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Flow<Location?> = callbackFlow {
        var lastLocationUpdate: Location? = null
        locationService.setup()

        try {
            kotlinx.coroutines.withTimeout(LOCATION_TIMEOUT) {
                locationService.startUpdates { location ->
                    lastLocationUpdate = location
                    if (location.accuracy != null && location.accuracy < locationService.minimumAccuracy) {
                        trySend(location)
                        locationService.stopUpdates()
                        channel.close()
                    }
                }
            }
            locationService.stopUpdates()
            trySend(lastLocationUpdate)
            channel.close()
        } catch (e: Exception) {
            locationService.stopUpdates()
            trySend(null)
        }

        awaitClose {
            locationService.stopUpdates()
        }
    }.flowOn(dispatcher.io)

    override suspend fun getLastLocation(): Location {
        val location = locationService.getLastLocation()
        return Location(location.latitude, location.longitude, location.accuracy)
    }

}