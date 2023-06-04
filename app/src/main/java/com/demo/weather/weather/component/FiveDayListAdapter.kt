package com.demo.weather.weather.component

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.weather.databinding.FiveDayComponentBinding
import com.demo.weather.weather.data.FiveDayWeather

class FiveDayListAdapter: ListAdapter<FiveDayWeather, FiveDayListAdapter.ViewHolder>(
    DiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    inner class ViewHolder(private val binding: FiveDayComponentBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {

        fun bind(data: FiveDayWeather) {
            binding.apply {

            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<FiveDayWeather>() {
        override fun areItemsTheSame(oldItem: FiveDayWeather, newItem: FiveDayWeather) =
            (
                    oldItem.weatherList == newItem.weatherList &&
                            oldItem.cnt == newItem.cnt
                    )

        override fun areContentsTheSame(oldItem: FiveDayWeather, newItem: FiveDayWeather) =
            oldItem == newItem
    }

}