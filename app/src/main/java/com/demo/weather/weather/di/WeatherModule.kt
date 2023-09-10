package com.demo.weather.weather.di

import com.demo.weather.history.HistoryRepository
import com.demo.weather.timer.DefaultTimer
import com.demo.weather.timer.Timer
import com.demo.weather.weather.WeatherRepository
import com.demo.weather.weather.helper.WeatherConstants.BASE_URL
import com.demo.weather.weather.io.RemoteWeatherDataSource
import com.demo.weather.weather.io.WeatherApi
import com.demo.weather.weather.io.WeatherDataSource
import com.demo.weather.weather.usecase.HourlyWeatherUseCase
import com.demo.weather.weather.usecase.SaveWeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
object WeatherModule {

    @Provides
    fun providesWeatherApi(client: OkHttpClient): WeatherApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(WeatherApi::class.java)

    @Provides
    fun providesHourlyWeatherUseCase(repo: WeatherRepository) = HourlyWeatherUseCase(repo)

    @Provides
    fun providesSaveWeatherUseCase(repo: HistoryRepository) = SaveWeatherUseCase(repo)

    @Provides
    fun providesWeatherDataSource(weatherApi: WeatherApi): WeatherDataSource =
        RemoteWeatherDataSource(weatherApi)

    @Provides
    fun providesWeatherRepository(dataSource: WeatherDataSource) =
        WeatherRepository(dataSource)

    @Provides
    fun providesTimer(): Timer = DefaultTimer()
}