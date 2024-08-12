package com.demo.weather.location.di

import android.content.Context
import com.demo.weather.common.helper.DispatcherProvider
import com.demo.weather.location.service.GoogleLocationService
import com.demo.weather.location.io.LocalLocationDataSource
import com.demo.weather.location.io.LocationDataSource
import com.demo.weather.location.LocationRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.demo.weather.location.service.LocationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object LocationModule {

    @Provides
    fun providesLocationDataSource(
        locationService: LocationService,
        dispatcherProvider: DispatcherProvider
    ): LocationDataSource = LocalLocationDataSource(locationService, dispatcherProvider)

    @Provides
    fun providesLocationService(
        locationClient: FusedLocationProviderClient,
        locationRequest: LocationRequest
    ): LocationService = GoogleLocationService(locationClient, locationRequest)

    @Provides
    fun providesWeatherRepository(dataSource: LocationDataSource) =
        LocationRepository(dataSource)

    @Provides
    fun providesLocationClient(@ApplicationContext context: Context): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @Provides
    fun providesLocationRequest(): LocationRequest =
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 100).build()
}