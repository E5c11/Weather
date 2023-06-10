package com.demo.weather.location.io

import com.demo.weather.common.io.ActionableException

class LocationFetchException(
    override var msg: String = "Could not fetch Location",
    override var error: Throwable? = null
): ActionableException()