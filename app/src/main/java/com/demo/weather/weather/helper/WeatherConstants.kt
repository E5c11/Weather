package com.demo.weather.weather.helper

import com.demo.weather.BuildConfig

object WeatherConstants {
    const val BASE_URL = "https://meteostat.p.rapidapi.com/"
    const val BASE_DATA_URL = "${BASE_URL}data/2.5/"
    const val STORAGE_URL = "https://openweathermap.org/img/wn/"
    const val API_KEY = BuildConfig.API_KEY
}