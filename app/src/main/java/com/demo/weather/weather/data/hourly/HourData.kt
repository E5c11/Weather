package com.demo.weather.weather.data.hourly

import com.demo.weather.R
import com.demo.weather.common.helper.getDate
import com.demo.weather.common.helper.toHour

data class HourData(
    val station: String,
    val time: String? = getDate().toHour(),
    val temp: Int? = 0,
    val rain : Int? = 0,
    val wind: Int? = 0,
    val icon: Int? = R.drawable.cloud
)