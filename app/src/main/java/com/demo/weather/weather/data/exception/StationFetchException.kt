package com.demo.weather.weather.data.exception

import com.demo.weather.R
import com.demo.weather.common.io.ActionableException

data class StationFetchException(
    override var msg: String = "Could not retrieve stations, you could look at your history in the meantime",
    override var error: Throwable? = null,
    override var navigate: Int? = R.id.historyFragment
): ActionableException(msg, error)