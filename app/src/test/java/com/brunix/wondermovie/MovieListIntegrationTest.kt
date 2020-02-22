package com.brunix.wondermovie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.brunix.data.source.LocalDataSource
import com.brunix.interactor.GetPopularMovies
import com.brunix.wondermovie.ui.main.MovieListViewModel
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieListIntegrationTest : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<MovieListViewModel.UiModel>

    private lateinit var viewmodel: MovieListViewModel

    @Before
    fun setUp() {
        val viewmodelModule = module {
            factory { MovieListViewModel(get(), get()) }
            factory { GetPopularMovies(get()) }
        }

        initMockedDi(viewmodelModule)

        viewmodel = get()
    }

    @Test
    fun `when local data source is empty data is loaded from server`() {
        viewmodel.model.observeForever(observer)

        viewmodel.onCoarsePermissionRequested()

        verify(observer).onChanged(MovieListViewModel.UiModel.Content(defaultFakeMovies))
    }

    @Test
    fun `when available data source is loaded from local`() {
        val fakeLocalMovies = listOf(mockedMovie.copy(12), mockedMovie.copy(13))

        val localDataSource = get<LocalDataSource>() as FakeLocalDataSource
        localDataSource.movies = fakeLocalMovies

        viewmodel.model.observeForever(observer)

        viewmodel.onCoarsePermissionRequested()

        verify(observer).onChanged(MovieListViewModel.UiModel.Content(fakeLocalMovies))
    }
}