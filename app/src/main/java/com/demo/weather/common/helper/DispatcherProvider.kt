package com.demo.weather.common.helper

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class DispatcherProvider(
    val io: CoroutineContext = Dispatchers.IO,
    val main: CoroutineContext = Dispatchers.Main
)
