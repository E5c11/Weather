package com.demo.weather.weather.viewmodel

import com.demo.weather.TestDispatcherRule
import com.demo.weather.location.viewmodel.LocationViewModel
import com.demo.weather.location.usecase.FetchLocationUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class LocationViewModelTest {

    @get:Rule
    val testDispatcherRule = TestDispatcherRule()

    private val useCase: FetchLocationUseCase = mockk()

    private val viewModel = LocationViewModel(useCase)

    @Test
    fun `obtainLocation should only be called once`() = runTest {
        every { useCase() } returns emptyFlow()

        viewModel.obtainLocation()

        verify(exactly = 1) { useCase() }
    }
}