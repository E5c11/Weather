package com.demo.weather.history.data.exception

import com.demo.weather.R
import com.demo.weather.common.io.ActionableException

data class HistoryFetchException(
    override var msg: String = "Could not retrieve local history, go to the Home page to get updated stats",
    override var error: Throwable? = null,
    override var navigate: Int? = R.id.weatherFragment
): ActionableException(msg, error)
