package com.brunix.interactor

import com.brunix.data.repository.MoviesRepository
import com.brunix.domain.Movie

class FindMovieById(private val moviesRepository: MoviesRepository) {

    suspend fun invoke(movieId: Int): Movie = moviesRepository.findById(movieId)

}