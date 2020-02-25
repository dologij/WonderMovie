package com.brunix.wondermovie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.brunix.data.source.LocalDataSource
import com.brunix.interactor.FindMovieById
import com.brunix.interactor.ToggleMovieFavorite
import com.brunix.wondermovie.ui.detail.MovieDetailViewModel
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieDetailIntegrationTests : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<MovieDetailViewModel.UiModel>

    private lateinit var viewmodel: MovieDetailViewModel

    private lateinit var localDataSource: FakeLocalDataSource

    @Before
    fun setUp() {
        val viewmodelModule = module {
            factory { (id: Int) -> MovieDetailViewModel(id, get(), get(), get()) }
            factory { FindMovieById(get()) }
            factory { ToggleMovieFavorite(get()) }
        }

        initMockedDi(viewmodelModule)

        viewmodel = get { parametersOf(2)}

        localDataSource = get<LocalDataSource>() as FakeLocalDataSource
        localDataSource.movies = defaultFakeMovies
    }

    @Test
    fun `observing LiveData finds the movie`() {
        viewmodel.model.observeForever(observer)

        verify(observer).onChanged(MovieDetailViewModel.UiModel(mockedMovie.copy(2)))
    }

    @Test
    fun `favorite is updated in local data source`() {
        viewmodel.model.observeForever(observer)

        viewmodel.onFavoriteClicked()

        runBlocking {
            assertTrue(localDataSource.findById(2).favorite)
        }
    }
}