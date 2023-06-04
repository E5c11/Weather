package com.demo.weather.weather.io

import com.demo.weather.weather.data.FiveDayWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {

    @GET("forecast")
    suspend fun getFiveDay(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("apiKey") apiKey: String
    ): Response<FiveDayWeather>

}