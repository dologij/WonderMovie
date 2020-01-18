package com.brunix.interactor

import com.brunix.data.repository.MoviesRepository
import com.brunix.domain.Movie

class ToggleMovieFavorite(private val moviesRepository: MoviesRepository) {

    suspend fun invoke(movie: Movie): Movie = with(movie) {
        copy(favorite = !favorite).also {moviesRepository.update(it)}
    }
}