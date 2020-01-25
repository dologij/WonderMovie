package com.brunix.wondermovie.di

import com.brunix.data.repository.MoviesRepository
import com.brunix.interactor.FindMovieById
import com.brunix.interactor.GetPopularMovies
import com.brunix.interactor.ToggleMovieFavorite
import dagger.Module
import dagger.Provides

@Module
class InteractorModule {

    @Provides
    fun getPopularMoviesProvider(moviesRepository: MoviesRepository) = GetPopularMovies(moviesRepository)

    @Provides
    fun findMovieByIdProvider(moviesRepository: MoviesRepository) = FindMovieById(moviesRepository)

    @Provides
    fun toggleMovieFavoriteProvider(moviesRepository: MoviesRepository) = ToggleMovieFavorite(moviesRepository)

}