package com.demo.weather.weather.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.weather.R
import com.demo.weather.databinding.FiveDayItemBinding
import com.demo.weather.weather.data.hourly.Weather

class HourlyListAdapter: ListAdapter<Weather, HourlyListAdapter.ViewHolder>(
    DiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FiveDayItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hour = getItem(position)
        holder.bind(hour)
    }

    inner class ViewHolder(
        private val binding: FiveDayItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Weather) {
            binding.apply {
                day.text = data.time
                icon.setImageResource(data.icon ?: R.drawable.cloud)
                description.text = root.context.getString(
                    R.string.day_weather,
                    data.temp.toString(),
                    data.wind.toString()
                )
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Weather>() {
        override fun areItemsTheSame(oldItem: Weather, newItem: Weather) =
            (oldItem.time == newItem.time && oldItem.temp == newItem.temp)

        override fun areContentsTheSame(oldItem: Weather, newItem: Weather) =
            oldItem == newItem
    }

}