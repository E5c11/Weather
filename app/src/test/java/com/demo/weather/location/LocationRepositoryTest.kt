package com.demo.weather.location

import app.cash.turbine.test
import com.demo.weather.common.helper.Constant.FINDING_LOCATION
import com.demo.weather.common.helper.Resource
import com.demo.weather.location.data.Location
import com.demo.weather.location.data.exception.LocationFetchException
import com.demo.weather.location.data.exception.LocationNotFoundException
import com.demo.weather.location.io.LocationDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class LocationRepositoryTest {

    private val dataSource: LocationDataSource = mockk()
    private val location: Location = mockk()

    private val repo = LocationRepository(dataSource)

    @Test
    fun `A Successful fetch returns LOADING then SUCCESS`() = runTest {
        every { location.latitude } returns -34.0
        every { location.longitude } returns 18.0
        val result = flowOf(location)
        coEvery { dataSource.getCurrentLocation() } returns result

        repo.fetch().test {
            val emission1 = awaitItem()
            emission1.status.shouldBe(Resource.Status.LOADING)
            emission1.loading.shouldBeEqualTo(FINDING_LOCATION)

            val emission2 = awaitItem()
            emission2.status.shouldBe(Resource.Status.SUCCESS)
            emission2.data?.longitude?.shouldBeEqualTo(18.0)
            emission2.data?.latitude?.shouldBeEqualTo(-34.0)
            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { dataSource.getCurrentLocation() }
    }

    @Test
    fun `A failed fetch from dataSource returns LOADING then ERROR`() = runTest {
        coEvery { dataSource.getCurrentLocation() } throws Exception()

        repo.fetch().test {
            val emission1 = awaitItem()
            emission1.status.shouldBe(Resource.Status.LOADING)
            emission1.loading.shouldBeEqualTo(FINDING_LOCATION)

            val emission2 = awaitItem()
            emission2.status.shouldBe(Resource.Status.ERROR)
            emission2.error.shouldBeInstanceOf(LocationFetchException::class.java)
            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { dataSource.getCurrentLocation() }
    }

    @Test
    fun `A failed fetch from dataSource throws Exception LOADING then ERROR`() = runTest {
        coEvery { dataSource.getCurrentLocation() } throws LocationNotFoundException()

        repo.fetch().test {
            val emission1 = awaitItem()
            emission1.status.shouldBe(Resource.Status.LOADING)
            emission1.loading.shouldBeEqualTo(FINDING_LOCATION)

            val emission2 = awaitItem()
            emission2.status.shouldBe(Resource.Status.ERROR)
            emission2.error.shouldBeInstanceOf(LocationNotFoundException::class.java)
            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { dataSource.getCurrentLocation() }
    }
}