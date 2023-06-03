package com.demo.weather.io

import com.demo.weather.data.FiveDayWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherApi {

    @GET("forecast?lat={lat}&lon={lon}&appid={apiKey}")
    suspend fun getFiveDay(
        @Path("lat") lat: String,
        @Path("lon") lon: String,
        @Path("apiKey") apiKey: String
    ): Response<FiveDayWeather>

}