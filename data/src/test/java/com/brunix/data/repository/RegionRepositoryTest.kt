package com.brunix.data.repository

import com.brunix.data.repository.RegionRepository.Companion.DEFAULT_REGION
import com.brunix.data.source.LocationDataSource
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegionRepositoryTest {

    @Mock
    lateinit var locationDataSource: LocationDataSource

    @Mock
    lateinit var permissionChecker: PermissionChecker

    private lateinit var regionRepository: RegionRepository

    @Before
    fun setUp() {
        regionRepository = RegionRepository(locationDataSource, permissionChecker)
    }

    @Test
    fun `returns default when coarse perm is not granted`() {
        runBlocking {
            whenever(permissionChecker.check(PermissionChecker.Permission.COARSE_LOCATION)).thenReturn(false)

            val region = regionRepository.findLastRegion()

            assertEquals(DEFAULT_REGION, region)
        }
    }

    @Test
    fun `returns region from location data source when perm is granted`() {
        runBlocking {

            whenever(permissionChecker.check(PermissionChecker.Permission.COARSE_LOCATION)).thenReturn(true)
            whenever(locationDataSource.findLastRegion()).thenReturn("FR")

            val region = regionRepository.findLastRegion()

            assertEquals("FR", region)
        }
    }
}