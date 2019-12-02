package com.brunix.wondermovie.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brunix.wondermovie.model.Movie

class MovieDetailViewModel(private val movie: Movie) : ViewModel() {

    class UiModel (val movie: Movie)

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) _model.value = UiModel(movie)
            return _model
        }

}
