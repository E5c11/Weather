package com.demo.weather.weather.viewmodel

import com.demo.weather.TestDispatcherRule
import com.demo.weather.weather.usecase.HourlyWeatherUseCase
import com.demo.weather.weather.usecase.SaveWeatherUseCase
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class WeatherViewModelTest {

    private val hourlyUseCase: HourlyWeatherUseCase = mockk()
    private val saveWeatherUseCase: SaveWeatherUseCase = mockk()

    private val viewModel = WeatherViewModel(hourlyUseCase, saveWeatherUseCase)

    @get:Rule
    val testDispatcherRule = TestDispatcherRule()

    @Test
    fun `obtainLocation should only be called once`() {
        every { hourlyUseCase(any(), any()) } returns emptyFlow()

        viewModel.getWeather(123.0, 321.0)

        verify(exactly = 1) { hourlyUseCase(any(), any()) }
    }

    @Test
    fun `saveWeather should only be called once`() = runTest {
        coEvery { saveWeatherUseCase(any()) } just Runs

        viewModel.saveWeather(emptyList())

        coVerifySequence { saveWeatherUseCase(any()) }
    }
}