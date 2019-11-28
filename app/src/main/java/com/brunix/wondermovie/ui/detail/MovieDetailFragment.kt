package com.brunix.wondermovie.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
class MovieDetailFragment : Fragment(), MovieDetailPresenter.View {

    /**
     * The movie this fragment is presenting.
     */
    private var movie: Movie? = null

    private val presenter = MovieDetailPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_MOVIE)) {
                // Load the movie specified by the fragment
                movie = it.getParcelable(ARG_MOVIE)
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
        presenter.onCreateView(this, movie)
    }

    override fun updateHeader(movie: Movie?) {
        activity?.toolbar_layout?.title = movie?.title
        activity?.movie_detail_image?.loadUrl("https://image.tmdb.org/t/p/w780${movie?.backdropPath}")
    }

    override fun updateDetails(movie: Movie?) {
        movie_detail_summary.text = movie?.overview
        movie?.let { movie_detail_info.setMovie(it) }
    }

    override fun onDestroyView() {
        presenter.onDestroyView()
        super.onDestroyView()
    }

    companion object {
        const val ARG_MOVIE = "MovieDetailFragment:movie"
    }
}
