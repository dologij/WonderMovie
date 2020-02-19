package com.brunix.data.repository

import com.brunix.data.source.LocalDataSource
import com.brunix.data.source.RemoteDataSource
import com.brunix.domain.Movie
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoviesRepositoryTest {

    @Mock
    lateinit var localDataSource: LocalDataSource

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    @Mock
    lateinit var regionRepository: RegionRepository

    lateinit var moviesRepository: MoviesRepository

    private val apiKey = "myAp1K3y"

    private val mockedMovie = Movie(
        0,
        "Title",
        "Overview",
        "01/01/2025",
        "",
        "",
        "EN",
        "Title",
        5.0,
        5.1,
        false
    )

    @Before
    fun setUp() {
        moviesRepository = MoviesRepository(localDataSource, remoteDataSource, regionRepository, apiKey)
    }

    @Test
    fun `findById calls local data source`() {
        runBlocking {
            // GIVEN
            val movie = mockedMovie.copy(id=1)
            whenever(localDataSource.findById(1)).thenReturn(movie)

            // WHEN
            val result = moviesRepository.findById(1)

            // THEN
            Assert.assertEquals(movie, result)
        }
    }

    @Test
    fun `update updates local data source`() {
        runBlocking {

            val movie = mockedMovie.copy(id=2)

            moviesRepository.update(movie)

            verify(localDataSource).update(movie)
        }
    }

    @Test
    fun `getPopularMovies retrieves from local data source first`() {
        runBlocking {

            val localMovies = listOf(mockedMovie.copy(3))
            whenever(localDataSource.isEmpty()).thenReturn(false)
            whenever(localDataSource.getPopularMovies()).thenReturn(localMovies)

            val result = moviesRepository.getPopularMovies()

            Assert.assertEquals(localMovies, result)
        }
    }

    @Test
    fun `getPopularMovies saves remote data to local`() {
        runBlocking {

            val remoteMovies = listOf(mockedMovie.copy(4))
            whenever(localDataSource.isEmpty()).thenReturn(true)
            whenever(remoteDataSource.getPopularMovies(any(), any())).thenReturn(remoteMovies)
            whenever(regionRepository.findLastRegion()).thenReturn("ES")

            moviesRepository.getPopularMovies()

            verify(localDataSource).saveMovies(remoteMovies)
        }
    }
}