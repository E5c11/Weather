package com.demo.weather.history.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.weather.R
import com.demo.weather.common.helper.WeatherDiffCallback
import com.demo.weather.common.helper.toDayOfTheWeek
import com.demo.weather.databinding.HistoryItemBinding
import com.demo.weather.weather.data.weather.Weather

class HistoryListAdapter: ListAdapter<Weather, HistoryListAdapter.ViewHolder>(
    WeatherDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weather = getItem(position)
        holder.bind(weather)
    }

    inner class ViewHolder(
        private val binding: HistoryItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(weather: Weather) = binding.apply {
            location.text = weather.station
            icon.setImageResource(weather.icon ?: R.drawable.cloud)
            day.text = weather.time?.toDayOfTheWeek()
            description.text = root.context.getString(
                R.string.day_weather,
                weather.temp.toString(),
                weather.wind.toString()
            )
        }

    }

}