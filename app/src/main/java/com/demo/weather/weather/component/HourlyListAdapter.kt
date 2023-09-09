package com.demo.weather.weather.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.demo.weather.R
import com.demo.weather.common.helper.getDayOfWeek
import com.demo.weather.common.helper.toCamelCase
import com.demo.weather.databinding.FiveDayItemBinding
import com.demo.weather.weather.data.hourly.HourData
import com.demo.weather.weather.data.hourly.Hourly
import com.demo.weather.weather.helper.WeatherConstants.STORAGE_URL

class HourlyListAdapter: ListAdapter<HourData, HourlyListAdapter.ViewHolder>(
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

        fun bind(data: HourData) {
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

    class DiffCallback : DiffUtil.ItemCallback<HourData>() {
        override fun areItemsTheSame(oldItem: HourData, newItem: HourData) =
            (oldItem.time == newItem.time && oldItem.temp == newItem.temp)

        override fun areContentsTheSame(oldItem: HourData, newItem: HourData) =
            oldItem == newItem
    }

}