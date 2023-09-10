package com.demo.weather.weather.usecase

import app.cash.turbine.test
import com.demo.weather.common.helper.Resource
import com.demo.weather.weather.WeatherRepository
import com.demo.weather.weather.data.stations.Station
import com.demo.weather.weather.data.weather.Weather
import com.google.android.gms.maps.model.LatLng
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import net.bytebuddy.matcher.ElementMatchers.any
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class HourlyWeatherUseCaseTest {
    private val repository: WeatherRepository = mockk()

    private val useCase = HourlyWeatherUseCase(repository)

    private val mockWeather = Weather(
        station = "FAYP0",
        latlng = LatLng(12.3, 32.1)
    )

    private val mockStation = Station(
        "Loaction",
        "FAYP0"
    )

    @Test
    fun `HourlyWeatherUseCase should call nearbyStations then get weather for the closest station`() = runTest {
        coEvery { repository.fetchNearbyStations(any(), any()) } returns listOf(mockStation)
        coEvery { repository.fetchWeather(any(), any(), any(), any()) } returns flowOf(Resource.loading(), Resource.success(listOf(mockWeather)))

        useCase(12.3, 32.1).test {
            val loading1 = awaitItem()
            loading1.status.shouldBeEqualTo(Resource.Status.LOADING)

            val loading2 = awaitItem()
            loading2.status.shouldBeEqualTo(Resource.Status.LOADING)

            val weather = awaitItem()
            weather.status.shouldBeEqualTo(Resource.Status.SUCCESS)
            weather.data!!.shouldBeEqualTo(listOf(mockWeather))
            cancelAndIgnoreRemainingEvents()
        }

        coVerifySequence {
            repository.fetchNearbyStations(any(), any())
            repository.fetchWeather(any(), any(), any(), any())
        }
    }
}