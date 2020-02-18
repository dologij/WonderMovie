package com.brunix.wondermovie.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.brunix.data.repository.MoviesRepository
import com.brunix.data.repository.RegionRepository
import com.brunix.interactor.FindMovieById
import com.brunix.interactor.ToggleMovieFavorite
import com.brunix.wondermovie.MyApplication
import com.brunix.wondermovie.R
import com.brunix.wondermovie.model.AndroidPermissionChecker
import com.brunix.wondermovie.model.PlayServicesLocationDataSource
import com.brunix.wondermovie.model.database.RoomDataSource
import com.brunix.wondermovie.model.server.MovieDbDataSource
import com.brunix.wondermovie.ui.common.app
import com.brunix.wondermovie.ui.common.getViewModel
import com.brunix.wondermovie.ui.common.loadUrl
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.movie_detail.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

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
    private var movieId: Int = 0

    private val viewModel: MovieDetailViewModel by currentScope.viewModel(this) {
        parametersOf(movieId)
    }

    private lateinit var myApp: MyApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_MOVIE)) {
                // Load the movie specified by the fragment
                movieId = it.getInt(ARG_MOVIE, -1)
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

//        activity?.app?.db?.let{RoomDataSource(it)}
        myApp = activity!!.app

        viewModel.model.observe(this, Observer(::updateUi))

        activity?.movie_detail_favorite_btn?.setOnClickListener { viewModel.onFavoriteClicked() }
    }

    private fun updateUi(model: MovieDetailViewModel.UiModel) = with(model.movie) {
        activity?.toolbar_layout?.title = title
        activity?.movie_detail_image?.loadUrl("https://image.tmdb.org/t/p/w780$backdropPath")
        movie_detail_summary.text = overview
        movie_detail_info.setMovie(this)

        val favIcon = if (favorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off
        activity?.movie_detail_favorite_btn!!.setImageDrawable(activity?.getDrawable(favIcon))
    }

    companion object {
        private const val ARG_MOVIE = "MovieDetailFragment:movie"

        /**
         * Create a new instance of MovieDetailFragment, initialized to
         * show the movie in 'movie'.
         */
        fun newInstance(movieId: Int?) = MovieDetailFragment().apply {
            // Supply movie input as an argument.
            arguments = bundleOf(ARG_MOVIE to movieId)
        }
    }
}
