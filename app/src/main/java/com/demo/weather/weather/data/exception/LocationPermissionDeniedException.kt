package com.demo.weather.weather.data.exception

import com.demo.weather.common.io.ActionableException

data class LocationPermissionDeniedException(
    override var msg: String = "Location permission Denied, please enable permission!",
    override var error: Throwable? = null
): ActionableException(msg, error)