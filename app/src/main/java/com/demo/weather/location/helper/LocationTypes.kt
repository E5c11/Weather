package com.demo.weather.location.helper

import com.demo.weather.common.helper.Resource
import com.demo.weather.location.data.Location
import kotlinx.coroutines.flow.Flow

typealias LocationFlow = Flow<Resource<Location>>