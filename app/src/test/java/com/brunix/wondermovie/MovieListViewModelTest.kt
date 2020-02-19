package com.brunix.wondermovie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.brunix.domain.Movie
import com.brunix.interactor.GetPopularMovies
import com.brunix.wondermovie.ui.main.MovieListViewModel
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getPopularMovies: GetPopularMovies

    @Mock
    lateinit var observer: Observer<MovieListViewModel.UiModel>

    private lateinit var viewmodel: MovieListViewModel

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
        viewmodel = MovieListViewModel(getPopularMovies, Dispatchers.Unconfined)
    }

    @Test
    fun `observing LiveData executes location permission request`() {
        viewmodel.model.observeForever(observer)

        verify(observer).onChanged(MovieListViewModel.UiModel.RequestLocationPermission)
    }

    @Test
    fun `loading is shown after requesting location permission`() {
        runBlocking {

            val movies = listOf(mockedMovie.copy(1))
            whenever(getPopularMovies.invoke()).thenReturn(movies)

            viewmodel.model.observeForever(observer)
            viewmodel.onCoarsePermissionRequested()

            verify(observer).onChanged(MovieListViewModel.UiModel.Loading)
        }
    }

    @Test
    fun `getPopularMovies gets called after requesting location permission`() {
        runBlocking {
            val movies = listOf(mockedMovie.copy(1))
            whenever(getPopularMovies.invoke()).thenReturn(movies)

            viewmodel.model.observeForever(observer)
            viewmodel.onCoarsePermissionRequested()

            verify(observer).onChanged(MovieListViewModel.UiModel.Content(movies))
        }
    }
}