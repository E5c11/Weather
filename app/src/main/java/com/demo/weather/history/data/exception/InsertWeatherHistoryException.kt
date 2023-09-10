package com.demo.weather.history.data.exception

import com.demo.weather.common.io.ActionableException

data class InsertWeatherHistoryException(
    override var msg: String = "Insert weather history failed",
    override var error: Throwable? = null
): ActionableException(msg, error)
