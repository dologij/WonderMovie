package com.brunix.wondermovie.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.brunix.wondermovie.R
import com.brunix.wondermovie.model.Movie
import com.brunix.wondermovie.ui.common.loadUrl
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.movie_detail.*

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a [MovieListActivity]
 * in two-pane mode (on tablets) or a [MovieDetailActivity]
 * on handsets.
 */
class MovieDetailFragment : Fragment(){

    /**
     * The movie this fragment is presenting.
     */
    private lateinit var movie: Movie

    private lateinit var viewModel: MovieDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_MOVIE)) {
                // Load the movie specified by the fragment
                // TODO Find out how to get rid of the type mismatch error if removing the tailing !!
                movie = it.getParcelable(ARG_MOVIE)!!
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(
            this,
            MovieDetailViewModelFactory(movie)
        )[MovieDetailViewModel::class.java]

        viewModel.model.observe(this, Observer(::updateUi))
    }

    fun updateUi(model: MovieDetailViewModel.UiModel) = with(model.movie) {
        activity?.toolbar_layout?.title = title
        activity?.movie_detail_image?.loadUrl("https://image.tmdb.org/t/p/w780$backdropPath")
        movie_detail_summary.text = overview
        movie_detail_info.setMovie(this)
    }

    companion object {
        private const val ARG_MOVIE = "MovieDetailFragment:movie"

        /**
         * Create a new instance of MovieDetailFragment, initialized to
         * show the movie in 'movie'.
         */
        fun newInstance(movie: Movie?) = MovieDetailFragment().apply {
            // Supply movie input as an argument.
            arguments = bundleOf(ARG_MOVIE to movie)
        }
    }
}
