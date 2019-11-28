package com.brunix.wondermovie.ui.detail

import com.brunix.wondermovie.model.Movie

class MovieDetailPresenter {

    interface View {
        fun updateHeader(movie: Movie?)
        fun updateDetails(movie: Movie?)
    }

    private var view: View? = null

    fun onCreateView(view: View, movie: Movie?) {
        this.view = view
        view.updateHeader(movie)
        view.updateDetails(movie)
    }

    fun onDestroyView() {
        view = null
    }
}
