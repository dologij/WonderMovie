package com.brunix.wondermovie.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brunix.domain.Movie
import com.brunix.interactor.GetPopularMovies
import com.brunix.wondermovie.ui.common.ScopedViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class MovieListViewModel(private val getPopularMovies: GetPopularMovies, uiDispatcher: CoroutineDispatcher) : ScopedViewModel(uiDispatcher) {

    sealed class UiModel {
        // UI state: The list of movies to show in the screen is loading
        object Loading : UiModel()
        // UI state: Show the list of movies in the screen
        data class Content(val movies: List<Movie>) : UiModel()
        // UI state: Go to the detail of a movie
        data class Navigation(val movie: Movie) : UiModel()
        // UI state: Ask for the permissions needed to get the location
        object RequestLocationPermission : UiModel()
    }

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) {
                refresh()
            }
            return _model
        }

    init {
        initScope()
    }

    private fun refresh() {
        _model.value = UiModel.RequestLocationPermission
    }

    fun onCoarsePermissionRequested() {
        launch {
            _model.value = UiModel.Loading
            _model.value = UiModel.Content(getPopularMovies.invoke())
            // No need for a hideProgress: the view will be responsible for that (the view assumes that
            // whenever there is no info loading, the progress will not be shown)
        }
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    fun onMovieClicked(movie: Movie) {
        _model.value = UiModel.Navigation(movie)
    }
}
