package com.brunix.wondermovie.model.server

import com.brunix.data.source.RemoteDataSource
import com.brunix.domain.Movie
import com.brunix.wondermovie.model.toDomainMovie

class MovieDbDataSource (private val movieDb: MovieDb) : RemoteDataSource {

    override suspend fun getPopularMovies(apiKey: String, region: String): List<Movie> =
        movieDb.service
            .listPopularMoviesAsync(apiKey, region).await()
            .results
            .map { it.toDomainMovie() }

}
