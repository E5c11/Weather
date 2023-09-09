package com.demo.weather.history.helper

import com.demo.weather.common.helper.Resource
import com.demo.weather.weather.data.weather.Weather
import kotlinx.coroutines.flow.Flow

typealias HistoryListFlow = Flow<Resource<List<Weather>>>
typealias HistoryFlow = Flow<Resource<Weather>>