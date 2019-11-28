package com.brunix.wondermovie.ui.main

import com.brunix.wondermovie.model.Movie
import com.brunix.wondermovie.model.MoviesRepository
import com.brunix.wondermovie.ui.common.Scope
import kotlinx.coroutines.launch

class MovieListPresenter(private val moviesRepository: MoviesRepository) : Scope by Scope.Impl() {

    interface View {
        fun showProgress()
        fun hideProgress()
        fun updateData(movies: List<Movie>)
        fun navigateTo(movie: Movie)
    }

    private var view: View? = null

    fun onCreate(view: View) {
        initScope()
        this.view = view

        launch {
            view.showProgress()
            view.updateData(moviesRepository.findPopularMovies().results)
            view.hideProgress()
        }
    }

    fun onDestroy() {
        cancelScope()
        this.view = null
    }

    fun onMovieClicked(movie: Movie) {
        view?.navigateTo(movie)
    }
}
