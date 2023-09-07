package com.demo.weather.location

import android.annotation.SuppressLint
import com.demo.weather.common.helper.DispatcherProvider
import com.demo.weather.timer.Timer
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LocalLocationDataSource @Inject constructor(
    private var locationService: LocationService,
    private val timer: Timer,
    private val dispatcher: DispatcherProvider
): LocationDataSource {

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Flow<Location?> = callbackFlow {
        var lastLocationUpdate: Location? = null
        locationService.setup()

        try {
            locationService.startUpdates { location ->
                lastLocationUpdate = location
                if (location.accuracy != null && location.accuracy < locationService.minimumAccuracy) {
                    trySend(location)
                    locationService.stopUpdates()
                    channel.close()
                }
            }
            timer.start(
                onFinish = {
                    locationService.stopUpdates()
                    trySend(lastLocationUpdate)
                    channel.close()
                }
            )
        } catch (e: Exception) {
            locationService.stopUpdates()
            trySend(null)
        }

        awaitClose {
            timer.cancel()
        }
    }.flowOn(dispatcher.io)

    override suspend fun getLastLocation(): Location {
        val location = locationService.getLastLocation()
        return Location(location.latitude, location.longitude, location.accuracy)
    }

}