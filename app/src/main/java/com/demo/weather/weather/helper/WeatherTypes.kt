package com.demo.weather.weather.helper

import com.demo.weather.common.helper.Resource
import com.demo.weather.weather.data.entity.FiveDayWeather
import kotlinx.coroutines.flow.Flow

typealias FiveDayFlow = Flow<Resource<FiveDayWeather>>