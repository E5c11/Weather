package com.demo.weather.weather.io

class WeatherFetchException(
    val msg: String = "Could not retrieve the weather",
    val error: Throwable? = null
): Exception(msg, error)