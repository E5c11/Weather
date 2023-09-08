package com.demo.weather.location.service

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.demo.weather.location.data.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.android.asCoroutineDispatcher
import kotlinx.coroutines.tasks.await

class GoogleLocationService(
    private val locationClient: FusedLocationProviderClient,
    private val locationRequest: LocationRequest
) : LocationService {
    private lateinit var dispatcherHandler: Handler
    private lateinit var locationCallback: LocationCallback
    private var minAccuracy = 100F

    override fun setup() {
        Looper.prepare()
        dispatcherHandler = Handler(Looper.myLooper()!!).also {
            it.asCoroutineDispatcher()
        }
    }

    @SuppressLint("MissingPermission")
    override fun startUpdates(duration: Long, location: (Location) -> Unit) {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (l in locationResult.locations) {
                    Log.d("myT", "onLocationResult: ${l.latitude}")
                    location(Location(l.latitude, l.longitude, l.accuracy))
                }
            }
        }
        locationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            dispatcherHandler.looper
        )
        Looper.loop()
    }

    override fun stopUpdates() {
        locationClient.removeLocationUpdates(locationCallback)
    }

    @SuppressLint("MissingPermission")
    override suspend fun getLastLocation(): Location {
        val location = locationClient.lastLocation.await()
        return Location(location?.latitude, location?.longitude, location?.accuracy)
    }

    override var minimumAccuracy: Float
        get() = minAccuracy
        set(value) { minAccuracy = value }
}