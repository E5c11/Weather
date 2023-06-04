package com.demo.weather.weather.usecase

import com.demo.weather.location.LocationRepository
import javax.inject.Inject

class FetchLocationUseCase @Inject constructor(
    private val repository: LocationRepository
) {
    operator fun invoke() = repository.fetch()
}