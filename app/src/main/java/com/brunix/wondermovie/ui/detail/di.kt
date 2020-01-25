package com.brunix.wondermovie.ui.detail

import com.brunix.data.repository.MoviesRepository
import com.brunix.interactor.FindMovieById
import com.brunix.interactor.GetPopularMovies
import com.brunix.interactor.ToggleMovieFavorite
import com.brunix.wondermovie.ui.main.MovieListViewModel
import dagger.Module
import dagger.Provides
import dagger.Subcomponent


@Module
class MovieDetailActivityModule(private val movieId: Int) {

    @Provides
    fun movieDetailViewModelProvider(
        findMovieById: FindMovieById,
        toggleMovieFavorite: ToggleMovieFavorite
    ): MovieDetailViewModel {
        return MovieDetailViewModel(movieId, findMovieById, toggleMovieFavorite)
    }

    @Provides
    fun findMovieByIdProvider(moviesRepository: MoviesRepository) =
        FindMovieById(moviesRepository)

    @Provides
    fun toggleMovieFavoriteProvider(moviesRepository: MoviesRepository) =
        ToggleMovieFavorite(moviesRepository)
}

@Subcomponent(modules = [(MovieDetailActivityModule::class)])
interface MovieDetailActivityComponent {
    val movieDetailViewModel: MovieDetailViewModel
}
