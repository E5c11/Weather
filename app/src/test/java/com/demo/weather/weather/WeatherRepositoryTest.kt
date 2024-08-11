package com.demo.weather.weather

import app.cash.turbine.test
import com.demo.weather.common.helper.Resource
import com.demo.weather.weather.data.exception.WeatherFetchException
import com.demo.weather.weather.data.stations.Station
import com.demo.weather.weather.data.weather.Weather
import com.demo.weather.weather.io.WeatherDataSource
import com.google.android.gms.maps.model.LatLng
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class WeatherRepositoryTest {

    private val dataSource: WeatherDataSource = mockk()

    private val repository = WeatherRepository(dataSource)

    private val mockWeather: List<Weather> = mockk()
    private val mockCurrentWeather: Weather = mockk()
    private val mockStation: List<Station> = mockk()

    @Test
    fun `a SUCCESSFUL fetchWeather should return LOADING THEN a flow of List of Weather`() = runTest {
        coEvery { dataSource.getWeather(any(), any(), any(), any()) } returns mockWeather

        repository.fetchWeather("123", "station", LatLng(0.0, 0.0)).test {
            val loadingItem = awaitItem()
            loadingItem.status.shouldBeEqualTo(Resource.Status.LOADING)

            val successItem = awaitItem()
            successItem.status.shouldBeEqualTo(Resource.Status.SUCCESS)
            successItem.data!!.shouldBeEqualTo(mockWeather)

            cancelAndIgnoreRemainingEvents()
        }

        coVerifySequence {
            dataSource.getWeather(any(), any(), any(), any())
        }
    }

    @Test
    fun `a FAILED fetchWeather should return LOADING THEN a WeatherFetchException`() = runTest {
        coEvery { dataSource.getWeather(any(), any(), any(), any()) } throws WeatherFetchException()

        repository.fetchWeather("123", "station", LatLng(0.0, 0.0)).test {
            val loadingItem = awaitItem()
            loadingItem.status.shouldBeEqualTo(Resource.Status.LOADING)

            val errorItem = awaitItem()
            errorItem.status.shouldBeEqualTo(Resource.Status.ERROR)
            errorItem.error!!.shouldBeInstanceOf(WeatherFetchException::class)

            cancelAndIgnoreRemainingEvents()
        }

        coVerifySequence {
            dataSource.getWeather(any(), any(), any(), any())
        }
    }

    @Test
    fun `a FAILED fetchWeather should return LOADING THEN catch generic Exception THEN cast it to a WeatherFetchException`() = runTest {
        coEvery { dataSource.getWeather(any(), any(), any(), any()) } throws Exception()

        repository.fetchWeather("123", "station", LatLng(0.0, 0.0)).test {
            val loadingItem = awaitItem()
            loadingItem.status.shouldBeEqualTo(Resource.Status.LOADING)

            val errorItem = awaitItem()
            errorItem.status.shouldBeEqualTo(Resource.Status.ERROR)
            errorItem.error!!.shouldBeInstanceOf(WeatherFetchException::class)

            cancelAndIgnoreRemainingEvents()
        }

        coVerifySequence {
            dataSource.getWeather(any(), any(), any(), any())
        }
    }

    @Test
    fun `a SUCCESSFUL fetchCurrentWeather should return LOADING THEN a flow of List of Weather`() = runTest {
        coEvery { dataSource.getCurrentWeather(any(), any(), any(), any()) } returns mockCurrentWeather

        repository.fetchCurrentWeather("123", "station", LatLng(0.0, 0.0)).test {
            val loadingItem = awaitItem()
            loadingItem.status.shouldBeEqualTo(Resource.Status.LOADING)

            val successItem = awaitItem()
            successItem.status.shouldBeEqualTo(Resource.Status.SUCCESS)
            successItem.data!!.shouldBeEqualTo(mockCurrentWeather)

            cancelAndIgnoreRemainingEvents()
        }

        coVerifySequence {
            dataSource.getCurrentWeather(any(), any(), any(), any())
        }
    }

    @Test
    fun `a FAILED fetchCurrentWeather should return LOADING THEN a WeatherFetchException`() = runTest {
        coEvery { dataSource.getCurrentWeather(any(), any(), any(), any()) } throws WeatherFetchException()

        repository.fetchCurrentWeather("123", "station", LatLng(0.0, 0.0)).test {
            val loadingItem = awaitItem()
            loadingItem.status.shouldBeEqualTo(Resource.Status.LOADING)

            val errorItem = awaitItem()
            errorItem.status.shouldBeEqualTo(Resource.Status.ERROR)
            errorItem.error!!.shouldBeInstanceOf(WeatherFetchException::class)

            cancelAndIgnoreRemainingEvents()
        }

        coVerifySequence {
            dataSource.getCurrentWeather(any(), any(), any(), any())
        }
    }

    @Test
    fun `a FAILED fetchCurrentWeather should return LOADING THEN catch generic Exception THEN cast it to a WeatherFetchException`() = runTest {
        coEvery { dataSource.getCurrentWeather(any(), any(), any(), any()) } throws Exception()

        repository.fetchCurrentWeather("123", "station", LatLng(0.0, 0.0)).test {
            val loadingItem = awaitItem()
            loadingItem.status.shouldBeEqualTo(Resource.Status.LOADING)

            val errorItem = awaitItem()
            errorItem.status.shouldBeEqualTo(Resource.Status.ERROR)
            errorItem.error!!.shouldBeInstanceOf(WeatherFetchException::class)

            cancelAndIgnoreRemainingEvents()
        }

        coVerifySequence {
            dataSource.getCurrentWeather(any(), any(), any(), any())
        }
    }

    @Test
    fun `fetchNearbyStations should return a list of Station`() = runTest {
        coEvery { dataSource.getStations(any(), any()) } returns mockStation

        repository.fetchNearbyStations(0.0, 0.0).shouldBeEqualTo(mockStation)

        coVerifySequence {
            dataSource.getStations(any(), any())
        }
    }

}