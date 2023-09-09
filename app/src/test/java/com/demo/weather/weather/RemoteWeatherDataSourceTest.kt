package com.demo.weather.weather

import com.demo.weather.weather.io.WeatherApi
import com.demo.weather.weather.data.exception.WeatherFetchException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.amshove.kluent.internal.assertFailsWith
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response
import java.io.IOException

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class RemoteWeatherDataSourceTest {

    private val weatherApi: WeatherApi = mockk()

    private val datasource = RemoteWeatherDataSource(weatherApi)

    private val mockData = FiveDayWeather(
        "",
        123,
        321,
        listOf<Weather>(),
        City()
    )

    @Test
    fun `A Successful getFiveDayWeather fetch returns FiveDayWeather object`() = runTest {
        val response = Response.success(mockData)
        coEvery { weatherApi.getHourly(any(), any(), any(), any(), any()) } returns response

        datasource.getFiveDayWeather(123L, 321L).shouldBeEqualTo(mockData)

        coVerify(exactly = 1) { weatherApi.getHourly(any(), any(), any(), any(), any()) }
    }

    @Test
    fun `A Failed getFiveDayWeather fetch throws WeatherFetchException`() = runTest {

        coEvery { weatherApi.getHourly(any(), any(), any(), any(), any()) } throws IOException()

        assertFailsWith<WeatherFetchException> { datasource.getFiveDayWeather(123L, 321L) }

        coVerify(exactly = 1) { weatherApi.getHourly(any(), any(), any(), any(), any()) }
    }

    @Test
    fun `A Failed getFiveDayWeather fetch returns a Exception from the API`() = runTest {
        val response = Response.error<FiveDayWeather>(401, "".toResponseBody(null))
        coEvery { weatherApi.getHourly(any(), any(), any(), any(), any()) } returns response

        assertFailsWith<WeatherFetchException> { datasource.getFiveDayWeather(123L, 321L) }

        coVerify(exactly = 1) { weatherApi.getHourly(any(), any(), any(), any(), any()) }
    }
}