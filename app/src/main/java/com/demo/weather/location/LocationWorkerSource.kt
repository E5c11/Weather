package com.demo.weather.location

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationWorkerSource {
    suspend fun getCurrentLocation(): Flow<Location>
}