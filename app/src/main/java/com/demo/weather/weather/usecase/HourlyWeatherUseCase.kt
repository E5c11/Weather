package com.demo.weather.weather.usecase

import com.demo.weather.common.helper.Resource
import com.demo.weather.common.io.ActionableException
import com.demo.weather.weather.WeatherRepository
import com.demo.weather.weather.data.exception.StationFetchException
import com.demo.weather.weather.data.exception.WeatherFetchException
import com.demo.weather.weather.helper.WeatherListFlow
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HourlyWeatherUseCase(
    private val repository: WeatherRepository
) {
    operator fun invoke(lat: Double, lng: Double): WeatherListFlow = flow {
        emit(Resource.loading())
        try {
            val stations = repository.fetchNearbyStations(lat, lng)
            val closestStation = stations[0]

            repository.fetchWeather(
                closestStation.id, closestStation.name, LatLng(lat, lng)
            ).collect { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> emit(Resource.success(resource.data!!))
                    Resource.Status.ERROR -> emit(Resource.error(resource.error))
                    Resource.Status.LOADING -> emit(Resource.loading())
                }
            }
        } catch (e: StationFetchException) {
            emit(Resource.error(e))
        } catch (e: WeatherFetchException) {
            emit(Resource.error(e))
        } catch (e: Exception) {
            emit(Resource.error(ActionableException(error = e)))
        }
    }
}