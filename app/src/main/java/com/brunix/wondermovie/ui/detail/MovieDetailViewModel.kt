package com.brunix.wondermovie.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brunix.wondermovie.model.Movie
import com.brunix.wondermovie.model.MoviesRepository
import com.brunix.wondermovie.ui.main.MovieListViewModel

class MovieDetailViewModel(private val movie: Movie) : ViewModel() {

    class UiModel (val movie: Movie)

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) {
                _model.value = UiModel(movie)
            }
            return _model
        }

}

@Suppress("UNCHECKED_CAST")
class MovieDetailViewModelFactory(private val movie: Movie) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        MovieDetailViewModel(movie) as T
}
