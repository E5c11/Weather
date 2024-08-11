package com.demo.weather.weather.io

import com.demo.weather.weather.data.exception.StationFetchException
import com.demo.weather.weather.data.exception.WeatherFetchException
import com.demo.weather.weather.data.stations.Station
import com.demo.weather.weather.data.stations.StationsDto
import com.demo.weather.weather.data.toHourly
import com.demo.weather.weather.data.toStation
import com.demo.weather.weather.data.weather.Weather
import com.demo.weather.weather.data.weather.WeatherDto
import com.google.android.gms.maps.model.LatLng
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.internal.assertFailsWith
import org.amshove.kluent.shouldBeEqualTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class RemoteWeatherDataSourceTest {

    private val weatherApi: WeatherApi = mockk()

    private val dataSource = RemoteWeatherDataSource(weatherApi)

    private val mockWeatherResponse: Response<WeatherDto> = mockk()
    private val mockWeatherDto: WeatherDto = mockk()
    private val mockWeather: List<Weather> = mockk()
    private val mockCurrentWeather: Weather = mockk()

    private val mockStationsResponse: Response<StationsDto> = mockk()
    private val mockStationsDto: StationsDto = mockk()
    private val mockStations: List<Station> = mockk()

    private val weatherMapperRoute = "com.demo.weather.weather.data.WeatherMapperKt"

    @Before
    fun setUp() {
        mockkStatic(weatherMapperRoute)
    }

    @After
    fun tearDown() {
        unmockkStatic(weatherMapperRoute)
    }

    @Test
    fun `a SUCCESSFUL getStations should return a list of Station THEN map to List of Weather`() = runTest {
        coEvery { weatherApi.getStations(any(), any()) } returns mockStationsResponse
        every { mockStationsResponse.body() } returns mockStationsDto
        every { mockStationsResponse.isSuccessful } returns true
        every { mockStationsDto.toStation() } returns mockStations

        val result = dataSource.getStations(0.0, 0.0)

        result.shouldBeEqualTo(mockStations)

        coVerifySequence {
            weatherApi.getStations(any(), any())
            mockStationsResponse.body()
            mockStationsResponse.isSuccessful
            mockStationsDto.toStation()
        }
    }

    @Test
    fun `a FAILED getStations FAILS with an Exception THEN casts to StationFetchException`() = runTest {
        coEvery { weatherApi.getStations(any(), any()) } throws Exception()

        assertFailsWith<StationFetchException> { dataSource.getStations(0.0, 0.0) }

        coVerifySequence {
            weatherApi.getStations(any(), any())
        }
    }

    @Test
    fun `a FAILED getStations RETURNS isSuccessful false should THROW StationFetchException`() = runTest {
        coEvery { weatherApi.getStations(any(), any()) } returns mockStationsResponse
        every { mockStationsResponse.body() } returns mockStationsDto
        every { mockStationsResponse.isSuccessful } returns false

        assertFailsWith<StationFetchException> { dataSource.getStations(0.0, 0.0) }

        coVerifySequence {
            weatherApi.getStations(any(), any())
            mockStationsResponse.body()
            mockStationsResponse.isSuccessful
        }
    }

    @Test
    fun `a FAILED getStations RETURNS null body false should THROW StationFetchException`() = runTest {
        coEvery { weatherApi.getStations(any(), any()) } returns mockStationsResponse
        every { mockStationsResponse.body() } returns null
        every { mockStationsResponse.isSuccessful } returns false

        assertFailsWith<StationFetchException> { dataSource.getStations(0.0, 0.0) }

        coVerifySequence {
            weatherApi.getStations(any(), any())
            mockStationsResponse.body()
            mockStationsResponse.isSuccessful
        }
    }

    @Test
    fun `a SUCCESSFUL getWeather should return a list of Weather THEN map to List of Weather`() = runTest {
        coEvery { weatherApi.getWeather(any(), any(), any(), any()) } returns mockWeatherResponse
        every { mockWeatherResponse.body() } returns mockWeatherDto
        every { mockWeatherResponse.isSuccessful } returns true
        every { mockWeatherDto.toHourly(any(), any()) } returns mockWeather

        val result = dataSource.getWeather("key", "name", mockk(), Frequency.HOURLY)

        result.shouldBeEqualTo(mockWeather)

        coVerifySequence {
            weatherApi.getWeather(any(), any(), any(), any())
            mockWeatherResponse.body()
            mockWeatherResponse.isSuccessful
            mockWeatherDto.toHourly(any(), any())
        }
    }

    @Test
    fun `a FAILED getWeather FAILS with an Exception THEN casts to WeatherFetchException`() = runTest {
        coEvery { weatherApi.getWeather(any(), any(), any(), any()) } throws Exception()

        assertFailsWith<WeatherFetchException> {
            dataSource.getWeather("key", "name", mockk(), Frequency.HOURLY)
        }

        coVerifySequence {
            weatherApi.getWeather(any(), any(), any(), any())
        }
    }

    @Test
    fun `a FAILED getWeather RETURNS null body false should THROW WeatherFetchException`() = runTest {
        coEvery { weatherApi.getWeather(any(), any(), any(), any()) } returns mockWeatherResponse
        every { mockWeatherResponse.body() } returns null
        every { mockWeatherResponse.isSuccessful } returns false

        assertFailsWith<WeatherFetchException> {
            dataSource.getWeather("key", "name", mockk(), Frequency.HOURLY)
        }

        coVerifySequence {
            weatherApi.getWeather(any(), any(), any(), any())
            mockWeatherResponse.body()
            mockWeatherResponse.isSuccessful
        }
    }

    @Test
    fun `a FAILED getWeather RETURNS isSuccessful false should THROW WeatherFetchException`() = runTest {
        coEvery { weatherApi.getWeather(any(), any(), any(), any()) } returns mockWeatherResponse
        every { mockWeatherResponse.body() } returns mockWeatherDto
        every { mockWeatherResponse.isSuccessful } returns false

        assertFailsWith<WeatherFetchException> {
            dataSource.getWeather("key", "name", mockk(), Frequency.HOURLY)
        }

        coVerifySequence {
            weatherApi.getWeather(any(), any(), any(), any())
            mockWeatherResponse.body()
            mockWeatherResponse.isSuccessful
        }
    }

    @Test
    fun `a SUCCESSFUL getCurrentWeather should return a Weather THEN map to List of Weather`() = runTest {
        coEvery { weatherApi.getWeather(any(), any(), any(), any()) } returns mockWeatherResponse
        every { mockWeatherResponse.body() } returns mockWeatherDto
        every { mockWeatherResponse.isSuccessful } returns true
        every { mockWeatherDto.toHourly(any(), any()) } returns mockWeather
        every { mockWeather.get(any()) } returns mockCurrentWeather

        val result = dataSource.getCurrentWeather("key", "name", LatLng(0.0, 0.0), Frequency.HOURLY)

        result.shouldBeEqualTo(mockCurrentWeather)

        coVerifySequence {
            weatherApi.getWeather(any(), any(), any(), any())
            mockWeatherResponse.body()
            mockWeatherResponse.isSuccessful
            mockWeatherDto.toHourly(any(), any())
            mockWeather.get(any())
        }
    }

    @Test
    fun `a FAILED getCurrentWeather FAILS with an Exception THEN casts to WeatherFetchException`() = runTest {
        coEvery { weatherApi.getWeather(any(), any(), any(), any()) } throws Exception()

        assertFailsWith<WeatherFetchException> {
            dataSource.getCurrentWeather("key", "name", LatLng(0.0, 0.0), Frequency.HOURLY)
        }

        coVerifySequence {
            weatherApi.getWeather(any(), any(), any(), any())
        }
    }

    @Test
    fun `a FAILED getCurrentWeather RETURNS null body false should THROW WeatherFetchException`() = runTest {
        coEvery { weatherApi.getWeather(any(), any(), any(), any()) } returns mockWeatherResponse
        every { mockWeatherResponse.body() } returns null
        every { mockWeatherResponse.isSuccessful } returns false

        assertFailsWith<WeatherFetchException> {
            dataSource.getCurrentWeather("key", "name", LatLng(0.0, 0.0), Frequency.HOURLY)
        }

        coVerifySequence {
            weatherApi.getWeather(any(), any(), any(), any())
            mockWeatherResponse.body()
            mockWeatherResponse.isSuccessful
        }
    }

}