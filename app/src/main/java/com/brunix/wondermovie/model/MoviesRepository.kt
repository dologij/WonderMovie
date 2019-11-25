package com.brunix.wondermovie.model

import android.app.Activity
import com.brunix.wondermovie.R

class MoviesRepository(activity: Activity) {

    private val apiKey = activity.getString(R.string.api_key)
    private val regionRepository = RegionRepository(activity)

    suspend fun findPopularMovies() =
        MovieDb.service
            .listPopularMoviesAsync(
                apiKey,
                regionRepository.findLastRegion()
            )
            .await()
}
