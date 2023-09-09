package com.demo.weather.weather.viewmodel

import com.demo.weather.weather.usecase.HourlyWeatherUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.emptyFlow
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class WeatherDtoViewModelTest {

    private val useCase: HourlyWeatherUseCase = mockk()

    private val viewModel = HourlyViewModel(useCase)

    @Test
    fun `obtainLocation should only be called once`() {
        every { useCase(any(), any()) } returns emptyFlow()

        viewModel.getWeather(123.0, 321.0)

        verify(exactly = 1) { useCase(any(), any()) }
    }
}