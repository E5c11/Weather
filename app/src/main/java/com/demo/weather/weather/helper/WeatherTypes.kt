package com.demo.weather.weather.helper

import com.demo.weather.common.helper.Resource
import com.demo.weather.weather.data.hourly.Hourly
import kotlinx.coroutines.flow.Flow

typealias HourlyFlow = Flow<Resource<Hourly>>