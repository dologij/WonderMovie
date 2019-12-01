package com.brunix.wondermovie.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.brunix.wondermovie.R
import com.brunix.wondermovie.model.MoviesRepository
import com.brunix.wondermovie.ui.common.startActivity
import com.brunix.wondermovie.ui.detail.MovieDetailActivity
import com.brunix.wondermovie.ui.main.MovieListViewModel.UiModel.*
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
class MovieListActivity : AppCompatActivity() {

    private lateinit var viewModel : MovieListViewModel

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    private lateinit var adapter: MoviesAdapter

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
        viewModel = ViewModelProviders.of(
            this,
            MovieListViewModelFactory(MoviesRepository(this))
        )[MovieListViewModel::class.java]

        adapter = MoviesAdapter(viewModel::onMovieClicked)
        recyclerView.adapter = adapter

        viewModel.model.observe(this, Observer(::updateUi))
    }

    private fun updateUi(model: MovieListViewModel.UiModel) {
        // The view assumes that whenever there is no info loading, the progress will not be shown
        progress.visibility = if (model == Loading) View.VISIBLE else View.GONE

        when (model) {
            is Content -> adapter.movies = model.movies
            is Navigation -> startActivity<MovieDetailActivity> {
                putExtra(MovieDetailActivity.ARG_MOVIE, model.movie)
            }
        }
    }
}
