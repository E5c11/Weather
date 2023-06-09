package com.demo.weather.weather.usecase

import com.demo.weather.weather.WeatherRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.emptyFlow
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FetchFiveDayWeatherUseCaseTest {
    private val repository: WeatherRepository = mockk()

    private val useCase = FetchFiveDayWeatherUseCase(repository)

    @Test
    fun `FetchFiveDayWeatherUseCase should only be called once`() {
        every { repository.fetch(any(), any()) } returns emptyFlow()

        useCase(123L, 321L)

        verify(exactly = 1) { repository.fetch(any(), any()) }
    }
}