package com.demo.weather.location.io

import com.demo.weather.common.io.ActionableException

class LocationNotFoundException(
    override var msg: String = "Could not find location please check that you have GPS or a network connection",
    override var error: Throwable? = null
): ActionableException(msg, error)