package com.demo.weather.weather.data.exception

import com.demo.weather.R
import com.demo.weather.common.io.ActionableException

data class WeatherNotFoundAtLocationException(
    override var msg: String = "Weather not found at Location. Either it's too cold or your phone is too old.",
    override var error: Throwable? = null,
    override var navigate: Int? = R.id.mapFragment
): ActionableException(msg, error)
