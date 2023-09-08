package com.demo.weather.location.io

import com.demo.weather.location.data.Location
import kotlinx.coroutines.flow.Flow

interface LocationDataSource {
    suspend fun getCurrentLocation(): Flow<Location?>
    suspend fun getLastLocation(): Location
}