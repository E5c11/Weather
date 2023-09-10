package com.demo.weather.weather.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.weather.R
import com.demo.weather.common.helper.WeatherDiffCallback
import com.demo.weather.common.helper.toHour
import com.demo.weather.databinding.HourlyItemBinding
import com.demo.weather.weather.data.weather.Weather

class HourlyListAdapter: ListAdapter<Weather, HourlyListAdapter.ViewHolder>(
    WeatherDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HourlyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hour = getItem(position)
        holder.bind(hour)
    }

    inner class ViewHolder(
        private val binding: HourlyItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Weather) = binding.apply {
            day.text = data.time?.toHour()
            icon.setImageResource(data.icon ?: R.drawable.cloud)
            description.text = root.context.getString(
                R.string.day_weather,
                data.temp.toString(),
                data.wind.toString()
            )
        }
    }
}