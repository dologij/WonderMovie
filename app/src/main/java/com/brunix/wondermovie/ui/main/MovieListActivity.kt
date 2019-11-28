package com.brunix.wondermovie.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.brunix.wondermovie.R
import com.brunix.wondermovie.model.Movie
import com.brunix.wondermovie.model.MoviesRepository
import com.brunix.wondermovie.ui.common.startActivity
import com.brunix.wondermovie.ui.detail.MovieDetailActivity
import kotlinx.android.synthetic.main.activity_movie_list.*
import kotlinx.android.synthetic.main.movie_list.*

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [MovieDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class MovieListActivity : AppCompatActivity(), MovieListPresenter.View {

    private val presenter by lazy { MovieListPresenter(MoviesRepository(this)) }

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    private val adapter = MoviesAdapter {
        presenter.onMovieClicked(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        if (movie_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        setupRecyclerView(movie_list)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = adapter

        presenter.onCreate(this)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun updateData(movies: List<Movie>) {
        adapter.movies = movies
    }

    override fun navigateTo(movie: Movie) {
        startActivity<MovieDetailActivity> {
            putExtra(MovieDetailActivity.ARG_MOVIE, movie)
        }
    }
}
