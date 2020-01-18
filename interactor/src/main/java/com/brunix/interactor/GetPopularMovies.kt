package com.brunix.interactor

import com.brunix.data.repository.MoviesRepository
import com.brunix.domain.Movie

class GetPopularMovies(
    private val moviesRepository: MoviesRepository
) {
    suspend fun invoke(): List<Movie> = moviesRepository.getPopularMovies()

}