package com.demo.weather.weather.usecase

import com.demo.weather.history.HistoryRepository
import com.demo.weather.weather.data.weather.Weather

class SaveWeatherUseCase(private val repo: HistoryRepository) {
    suspend operator fun invoke(weather: List<Weather>) = repo.save(weather)
}