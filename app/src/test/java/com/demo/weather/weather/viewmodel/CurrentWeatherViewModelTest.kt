package com.demo.weather.weather.viewmodel

import com.demo.weather.weather.usecase.FetchLocationUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.emptyFlow
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CurrentWeatherViewModelTest {

    private val useCase: FetchLocationUseCase = mockk()

    private val viewModel = CurrentWeatherViewModel(useCase)

    @Test
    fun `obtainLocation should only be called once`() {
        every { useCase() } returns emptyFlow()

        viewModel.obtainLocation()

        verify(exactly = 1) { useCase() }
    }
}