package com.brunix.wondermovie.model

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDbService {
    @GET("discover/movie?sort_by=popularity.desc")
    fun listPopularMoviesAsync(
        @Query("api_key") apiKey: String,
        @Query("region") region: String
    ): Deferred<MovieDbResult>
}
