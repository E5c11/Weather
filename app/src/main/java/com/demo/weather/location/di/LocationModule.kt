package com.demo.weather.location.di

import android.content.Context
import com.demo.weather.common.helper.DispatcherProvider
import com.demo.weather.location.LocalLocationDataSource
import com.demo.weather.location.LocationDataSource
import com.demo.weather.location.LocationRepository
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
        @ApplicationContext context: Context,
        dispatcherProvider: DispatcherProvider
    ): LocationDataSource =
        LocalLocationDataSource(context, dispatcherProvider)

    @Provides
    fun providesWeatherRepository(dataSource: LocationDataSource) =
        LocationRepository(dataSource)
}