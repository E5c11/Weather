package com.demo.weather.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import com.demo.weather.common.helper.DispatcherProvider
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.android.asCoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LocalLocationDataSource @Inject constructor(
    context: Context,
    private val dispatcher: DispatcherProvider
): LocationDataSource {

    private var locationClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 100).build()

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Flow<Location> = callbackFlow<Location> {
        Looper.prepare()
        val dispatcherHandler = HandlerThread("locationThread")
            .apply { start() }
            .looper.let { Handler(it) }
        dispatcherHandler.asCoroutineDispatcher()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    trySend(location)
                    channel.close()
                }
            }
        }

        locationClient.requestLocationUpdates(
            locationRequest, locationCallback, dispatcherHandler.looper
        )
        Looper.loop()
        awaitClose {
            locationClient.removeLocationUpdates(locationCallback)
        }
    }.flowOn(dispatcher.io)


}