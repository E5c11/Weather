package com.demo.weather.weather

import app.cash.turbine.test
import com.demo.weather.common.helper.Constant.LOADING
import com.demo.weather.common.helper.Resource
import com.demo.weather.common.io.ActionableException
import com.demo.weather.weather.data.City
import com.demo.weather.weather.data.entity.FiveDayWeather
import com.demo.weather.weather.data.Weather
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class WeatherRepositoryTest {

    private val dataSource: WeatherDataSource = mockk()

    private val repo = WeatherRepository(dataSource)

    private val mockData = FiveDayWeather(
        "",
        123,
        321,
        listOf<Weather>(),
        City()
    )

    @Test
    fun `A Successful fetch returns a flow of LOADING then SUCCESS`() = runTest {
        coEvery { dataSource.getFiveDayWeather(any(), any()) } returns mockData

        repo.fetch(123L, 321L).test {
            val emission1 = awaitItem()
            emission1.status.shouldBe(Resource.Status.LOADING)
            emission1.loading.shouldBeEqualTo(LOADING)

            val emission2 = awaitItem()
            emission2.status.shouldBe(Resource.Status.SUCCESS)
            emission2.data.shouldBeEqualTo(mockData)
            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { dataSource.getFiveDayWeather(any(), any()) }
    }

    @Test
    fun `A Failed fetch which fails on calling datastore throws an exception`() = runTest {
        coEvery { dataSource.getFiveDayWeather(any(), any()) } throws Exception()

        repo.fetch(123L, 321L).test {
            val emission1 = awaitItem()
            emission1.status.shouldBe(Resource.Status.LOADING)
            emission1.loading.shouldBeEqualTo(LOADING)

            val emission2 = awaitItem()
            emission2.status.shouldBe(Resource.Status.ERROR)
            emission2.error.shouldBeInstanceOf(ActionableException::class.java)
            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { dataSource.getFiveDayWeather(any(), any()) }
    }

}