package com.demo.weather.common.helper

import androidx.recyclerview.widget.DiffUtil
import com.demo.weather.weather.data.weather.Weather

class WeatherDiffCallback : DiffUtil.ItemCallback<Weather>() {
    override fun areItemsTheSame(oldItem: Weather, newItem: Weather) =
        (oldItem.time == newItem.time && oldItem.temp == newItem.temp)

    override fun areContentsTheSame(oldItem: Weather, newItem: Weather) =
        oldItem == newItem
}