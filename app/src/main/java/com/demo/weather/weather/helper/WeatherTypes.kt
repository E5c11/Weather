package com.demo.weather.weather.helper

import com.demo.weather.common.helper.Resource
import com.demo.weather.weather.data.weather.Weather
import kotlinx.coroutines.flow.Flow

typealias WeatherListFlow = Flow<Resource<List<Weather>>>
typealias WeatherFlow = Flow<Resource<Weather>>