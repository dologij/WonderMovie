package com.brunix.wondermovie

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brunix.wondermovie.model.Movie
import kotlinx.android.synthetic.main.movie_list_content.view.*

class MoviesAdapter(private val listener: (Movie) -> Unit) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    var movies: List<Movie> by basicDiffUtil(emptyList(), areItemsTheSame = {old, new -> old.id == new.id})

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.movie_list_content, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener {listener(movie)}
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) {
            itemView.movie_title.text = movie.title
            itemView.movie_cover.loadUrl("https://image.tmdb.org/t/p/w185/${movie.posterPath}")
        }
    }

}
