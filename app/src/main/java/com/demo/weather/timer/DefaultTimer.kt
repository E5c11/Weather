package com.demo.weather.timer

import android.os.CountDownTimer

class DefaultTimer : Timer {

    private lateinit var timer: CountDownTimer

    override fun start(duration: Long, interval: Long, onFinish: () -> Unit, onTick: () -> Unit) {
        timer = object : CountDownTimer(duration, interval) {
            override fun onTick(millisUntilFinished: Long) {
                onTick()
            }

            override fun onFinish() {
                onFinish()
            }
        }.start()
    }

    override fun cancel() {
        if (this::timer.isInitialized) timer.cancel()
    }
}