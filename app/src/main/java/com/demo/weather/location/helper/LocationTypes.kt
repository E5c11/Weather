package com.demo.weather.location.helper

import android.location.Location
import com.demo.weather.common.helper.Resource
import kotlinx.coroutines.flow.Flow

typealias LocationFlow = Flow<Resource<Location>>