package com.demo.weather.timer

interface Timer {
    fun start(
        duration: Long = 30000,
        interval: Long = 500,
        onFinish: () -> Unit,
        onTick: () -> Unit = {}
    )
    fun cancel()
}