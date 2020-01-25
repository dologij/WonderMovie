package com.brunix.wondermovie.di

import com.brunix.interactor.FindMovieById
import com.brunix.interactor.GetPopularMovies
import com.brunix.interactor.ToggleMovieFavorite
import com.brunix.wondermovie.ui.detail.MovieDetailViewModel
import com.brunix.wondermovie.ui.main.MovieListViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModelsModule {

    @Provides
    fun movieListViewModelProvider(getPopularMovies: GetPopularMovies) = MovieListViewModel(getPopularMovies)

    @Provides
    fun movieDetailViewModelProvider(
        findMovieById: FindMovieById,
        toggleMovieFavorite: ToggleMovieFavorite
    ): MovieDetailViewModel {
        // TODO Provide movieId here (to be done in the corresponding submodule later)
        return MovieDetailViewModel(-1, findMovieById, toggleMovieFavorite)
    }

}