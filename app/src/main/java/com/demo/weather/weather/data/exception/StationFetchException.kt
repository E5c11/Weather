package com.demo.weather.weather.data.exception

import com.demo.weather.common.io.ActionableException

data class StationFetchException(
    override var msg: String = "Could not retrieve stations",
    override var error: Throwable? = null
): ActionableException(msg, error)