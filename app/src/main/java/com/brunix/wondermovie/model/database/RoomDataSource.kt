package com.brunix.wondermovie.model.database

import com.brunix.data.source.LocalDataSource
import com.brunix.wondermovie.model.toDomainMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.brunix.domain.Movie as DomainMovie
import com.brunix.wondermovie.model.toRoomMovie
import com.brunix.wondermovie.model.database.Movie as RoomMovie

class RoomDataSource(db: MovieDatabase) : LocalDataSource {

    private val movieDao = db.movieDao()

    override suspend fun isEmpty(): Boolean = withContext(Dispatchers.IO) {
        movieDao.movieCount() <= 0
    }

    override suspend fun saveMovies(movies: List<DomainMovie>) {
        withContext(Dispatchers.IO) {
            movieDao.insertMovies(movies.map(DomainMovie::toRoomMovie))
        }
    }

    override suspend fun getPopularMovies(): List<DomainMovie> = withContext(Dispatchers.IO) {
        movieDao.getAll().map(RoomMovie::toDomainMovie)
    }

    override suspend fun findById(id: Int): DomainMovie = withContext(Dispatchers.IO) {
        movieDao.findById(id).toDomainMovie()
    }

    override suspend fun update(movie: DomainMovie) {
        withContext(Dispatchers.IO) { movieDao.updateMovie(movie.toRoomMovie()) }
    }
}
