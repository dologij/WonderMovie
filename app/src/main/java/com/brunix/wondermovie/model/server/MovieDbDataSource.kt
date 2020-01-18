package com.brunix.wondermovie.model.server

import com.brunix.data.source.RemoteDataSource
import com.brunix.domain.Movie
import com.brunix.wondermovie.model.toDomainMovie

class MovieDbDataSource : RemoteDataSource{

    override suspend fun getPopularMovies(apiKey: String, region: String): List<Movie> {
        return MovieDb.service
            .listPopularMoviesAsync(apiKey, region).await()
            .results
            .map { it.toDomainMovie() }
    }

}
