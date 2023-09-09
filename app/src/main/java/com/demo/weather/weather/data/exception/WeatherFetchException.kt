package com.demo.weather.weather.data.exception

import com.demo.weather.common.io.ActionableException

class WeatherFetchException(
    override var msg: String = "Could not retrieve the weather",
    override var error: Throwable? = null
): ActionableException(msg, error)