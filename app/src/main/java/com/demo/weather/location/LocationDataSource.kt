package com.demo.weather.location

import kotlinx.coroutines.flow.Flow

interface LocationDataSource {
    suspend fun getCurrentLocation(): Flow<Location?>
    suspend fun getLastLocation(): Location
}