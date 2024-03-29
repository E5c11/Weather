package com.demo.weather.weather.io

import com.demo.weather.BuildConfig
import com.demo.weather.weather.data.weather.WeatherDto
import com.demo.weather.weather.data.stations.StationsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherApi {

    @Headers(
        "x-rapidapi-key: ${BuildConfig.API_KEY}",
        "x-rapidapi-host: meteostat.p.rapidapi.com"
    )
    @GET("stations/nearby")
    suspend fun getStations(
        @Query("lat") lat: String,
        @Query("lon") lon: String
    ): Response<StationsDto>

    @Headers(
        "x-rapidapi-key: ${BuildConfig.API_KEY}",
        "x-rapidapi-host: meteostat.p.rapidapi.com"
    )
    @GET("stations/hourly")
    suspend fun getWeather(
        @Query("station") station: String,
        @Query("start") start: String,
        @Query("end") end: String,
        @Query("freq") frequency: String
    ): Response<WeatherDto>

}

enum class Frequency(val value: String) {
    HOURLY("H"), DAILY("D"), WEEKLY("W"), MONTHLY("M")
}