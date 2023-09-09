package com.demo.weather.weather.data.exception

import com.demo.weather.common.io.ActionableException

data class WeatherNotFoundAtLocationException(
    override var msg: String = "Weather not found at Location",
    override var error: Throwable? = null
): ActionableException(msg, error)
