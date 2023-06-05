package com.demo.weather.weather.io

import com.demo.weather.common.io.ActionableException

class LocationPermissionDeniedException(
    override var msg: String = "Location permission Denied",
    override var error: Throwable? = null
): ActionableException(msg, error)