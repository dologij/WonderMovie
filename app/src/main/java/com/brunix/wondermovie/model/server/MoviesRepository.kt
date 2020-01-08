package com.brunix.wondermovie.model.server

import com.brunix.wondermovie.MyApplication
import com.brunix.wondermovie.R
import com.brunix.wondermovie.model.RegionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.brunix.wondermovie.model.database.Movie as DbMovie
import com.brunix.wondermovie.model.server.Movie as ServerMovie

class MoviesRepository(application: MyApplication) {

    private val apiKey = application.getString(R.string.api_key)
    private val regionRepository =
        RegionRepository(application)
    private val db = application.db

    suspend fun findPopularMovies(): List<DbMovie> = withContext(Dispatchers.IO) {
        with(db.movieDao()) {
            if (movieCount() <= 0) {

                val movies = MovieDb.service
                    .listPopularMoviesAsync(
                        apiKey,
                        regionRepository.findLastRegion()
                    )
                    .await()
                    .results

                insertMovies(movies.map(ServerMovie::convertToDbMovie))
            }
            getAll()
        }
    }

    suspend fun findById(id: Int): DbMovie = withContext(Dispatchers.IO) {
        db.movieDao().findById(id)
    }

    suspend fun update(movie: DbMovie) = withContext(Dispatchers.IO) {
        db.movieDao().updateMovie(movie)
    }
}

private fun ServerMovie.convertToDbMovie() = DbMovie(
    0,
    title,
    overview,
    releaseDate,
    posterPath,
    backdropPath,
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage,
    false
)
