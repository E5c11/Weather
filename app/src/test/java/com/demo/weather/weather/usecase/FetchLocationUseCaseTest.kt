package com.demo.weather.weather.usecase

import com.demo.weather.location.LocationRepository
import com.demo.weather.location.usecase.FetchLocationUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.emptyFlow
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FetchLocationUseCaseTest {

    private val repository: LocationRepository = mockk()

    private val useCase = FetchLocationUseCase(repository)

    @Test
    fun `FetchLocationUseCase should only be called once`() {
        every { repository.fetch() } returns emptyFlow()

        useCase()

        verify(exactly = 1) { repository.fetch() }
    }
}