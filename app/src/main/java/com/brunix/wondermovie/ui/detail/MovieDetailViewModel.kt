package com.brunix.wondermovie.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brunix.domain.Movie
import com.brunix.interactor.FindMovieById
import com.brunix.interactor.ToggleMovieFavorite
import com.brunix.wondermovie.ui.common.ScopedViewModel
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val movieId: Int,
    private val findMovieById: FindMovieById,
    private val toggleMovieFavorite: ToggleMovieFavorite
) :
    ScopedViewModel() {

    class UiModel (val movie: Movie)

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) findMovie()
            return _model
        }

    private fun findMovie() = launch {
        _model.value = UiModel(findMovieById.invoke(movieId))
    }

    fun onFavoriteClicked() = launch {
        _model.value?.movie?.let {
            val updatedMovie = it.copy(favorite = !it.favorite)
            _model.value = UiModel(toggleMovieFavorite.invoke(it))
        }
    }
}
