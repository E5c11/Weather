package com.demo.weather.weather.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.demo.weather.R
import com.demo.weather.databinding.FiveDayItemBinding
import com.demo.weather.weather.data.Weather
import com.demo.weather.weather.helper.WeatherConstants.BASE_URL

class FiveDayListAdapter: ListAdapter<Weather, FiveDayListAdapter.ViewHolder>(
    DiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FiveDayItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weather = getItem(position)
        holder.bind(weather)
    }

    inner class ViewHolder(
        private val binding: FiveDayItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Weather) {
            binding.apply {
                Glide.with(root.context)
                    .load("${BASE_URL}img/wn${data.description[0].icon}@2x.png")
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(icon)
                day.text = data.dt.toString()
                description.text = root.context.getString(
                    R.string.day_summary,
                    data.description[0].description,
                    data.mainWeather.tempMax.toString(),
                    data.mainWeather.tempMin.toString()
                )
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Weather>() {
        override fun areItemsTheSame(oldItem: Weather, newItem: Weather) =
            (oldItem.mainWeather == newItem.mainWeather && oldItem.dt == newItem.dt)

        override fun areContentsTheSame(oldItem: Weather, newItem: Weather) =
            oldItem == newItem
    }

}