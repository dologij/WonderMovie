package com.brunix.wondermovie.ui.main

import com.brunix.data.repository.MoviesRepository
import com.brunix.interactor.GetPopularMovies
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class MovieListActivityModule {

    @Provides
    fun movieListViewModelProvider(getPopularMovies: GetPopularMovies) =
        MovieListViewModel(getPopularMovies)

    @Provides
    fun getPopularMoviesProvider(moviesRepository: MoviesRepository) =
        GetPopularMovies(moviesRepository)
}

@Subcomponent(modules = [(MovieListActivityModule::class)])
interface MovieListActivityComponent {
    val movieListViewModel: MovieListViewModel
}