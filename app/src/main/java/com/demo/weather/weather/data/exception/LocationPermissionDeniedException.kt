package com.demo.weather.weather.data.exception

import com.demo.weather.R
import com.demo.weather.common.io.ActionableException

data class LocationPermissionDeniedException(
    override var msg: String = "Location permission Denied, please enable permission or explore the map",
    override var error: Throwable? = null,
    override var navigate: Int? = R.id.mapFragment
): ActionableException(msg, error)