package com.brunix.wondermovie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.brunix.domain.Movie
import com.brunix.interactor.FindMovieById
import com.brunix.interactor.ToggleMovieFavorite
import com.brunix.wondermovie.ui.detail.MovieDetailViewModel
import com.brunix.wondermovie.ui.main.MovieListViewModel
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieDetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var findMovieById: FindMovieById

    @Mock
    lateinit var toggleMovieFavorite: ToggleMovieFavorite

    @Mock
    lateinit var observer: Observer<MovieDetailViewModel.UiModel>

    private lateinit var viewmodel: MovieDetailViewModel

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
        viewmodel = MovieDetailViewModel(2, findMovieById, toggleMovieFavorite, Dispatchers.Unconfined)
    }

    @Test
    fun `toggleMovieFavorite interactor is invoked when clicking favorite`() {
        runBlocking {
            val movie = mockedMovie.copy(2)
            whenever(findMovieById.invoke(2)).thenReturn(movie)
            whenever(toggleMovieFavorite.invoke(movie)).thenReturn(movie.copy(favorite = !movie.favorite))

            viewmodel.model.observeForever(observer)
            viewmodel.onFavoriteClicked()

            verify(toggleMovieFavorite).invoke(movie)
        }

    }

    @Test
    fun `observing LiveData finds the movie`() {
        runBlocking {
            val movie = mockedMovie.copy(2)
            whenever(findMovieById.invoke(2)).thenReturn(movie)

            viewmodel.model.observeForever(observer)

            verify(observer).onChanged(MovieDetailViewModel.UiModel(movie))
        }
    }
}