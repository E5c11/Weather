package com.demo.weather.history.usecase

import com.demo.weather.common.helper.Resource
import com.demo.weather.common.helper.matchTime
import com.demo.weather.history.HistoryRepository
import com.demo.weather.history.data.exception.HistoryFetchException
import com.demo.weather.history.helper.HistoryListFlow
import kotlinx.coroutines.flow.flow

class FetchRecentLocationsWeatherUseCase(private val repo: HistoryRepository) {
    operator fun invoke(): HistoryListFlow = flow {
        emit(Resource.loading())
        try {
            repo.fetchAll().collect { resource ->
                when (resource.status) {
                    Resource.Status.ERROR -> emit(Resource.error(resource.error))
                    Resource.Status.LOADING -> emit(Resource.loading())
                    Resource.Status.SUCCESS -> {
                        val mappedList = resource.data!!.map { list ->
                            list.first { it.time != null && it.time.matchTime() }
                        }
                        emit(Resource.success(mappedList))
                    }
                }
            }
        } catch (e: Exception) {
            emit(Resource.error(HistoryFetchException(error = e)))
        }
    }
}