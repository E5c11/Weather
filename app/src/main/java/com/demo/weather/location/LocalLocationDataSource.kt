package com.demo.weather.location

import android.annotation.SuppressLint
import android.location.Location
import android.os.CountDownTimer
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import com.demo.weather.common.helper.DispatcherProvider
import com.demo.weather.location.io.LocationNotFoundException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.android.asCoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

const val LOCATION_THREAD = "location.thread"

class LocalLocationDataSource @Inject constructor(
    private var locationClient: FusedLocationProviderClient,
    private val locationRequest: LocationRequest,
    private val dispatcher: DispatcherProvider
): LocationDataSource {

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Flow<Location> = callbackFlow<Location> {
        Looper.prepare()
        val dispatcherHandler = HandlerThread(LOCATION_THREAD)
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

        val timer = object: CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) { }

            override fun onFinish() {
                channel.close()
                throw LocationNotFoundException()
            }
        }
        timer.start()

        awaitClose {
            locationClient.removeLocationUpdates(locationCallback)
        }
    }.flowOn(dispatcher.io)

}