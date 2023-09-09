package com.demo.weather.weather.data

import com.demo.weather.common.helper.toHour
import com.demo.weather.common.helper.weatherIcon
import com.demo.weather.weather.data.hourly.HourData
import com.demo.weather.weather.data.hourly.Hourly
import com.demo.weather.weather.data.hourly.HourlyDto

fun HourlyDto.toHourly(name: String): Hourly = Hourly(
    this.data.mapNotNull {
        HourData(
            station = name.split("/")[0],
            time = it.time?.toHour(),
            temp = it.temp?.toInt(),
            rain = it.prcp?.toInt(),
            wind = it.wspd?.toInt(),
            icon = it.coco?.weatherIcon()
        )
    }
)

