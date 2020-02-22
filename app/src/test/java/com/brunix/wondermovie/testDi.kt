package com.brunix.wondermovie

import com.brunix.data.repository.PermissionChecker
import com.brunix.data.source.LocalDataSource
import com.brunix.data.source.LocationDataSource
import com.brunix.data.source.RemoteDataSource
import com.brunix.domain.Movie
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

private val mockedAppModule = module {
    single(named("apiKey")) { "4p1K3y"}
    single<LocalDataSource> { FakeLocalDataSource() }
    single<RemoteDataSource> { FakeRemoteDataSource() }
    single<LocationDataSource> { FakeLocationDataSource() }
    single<PermissionChecker> { FakePermissionChecker() }
    single { Dispatchers.Unconfined }
}

class FakeLocalDataSource : LocalDataSource {
    var movies: List<Movie> = emptyList()

    override suspend fun isEmpty() = movies.isEmpty()

    override suspend fun saveMovies(movies: List<Movie>) {
        this.movies = movies
    }

    override suspend fun getPopularMovies() = movies

    override suspend fun findById(id: Int) = movies.first { it.id == id }

    override suspend fun update(movie: Movie) {
        movies = movies.filterNot { it.id == movie.id } + movie
    }
}

class FakeRemoteDataSource : RemoteDataSource {
    var movies = defaultFakeMovies

    override suspend fun getPopularMovies(apiKey: String, region: String) = movies
}

class FakeLocationDataSource : LocationDataSource {
    var location = "US"

    override suspend fun findLastRegion(): String? = location
}

class FakePermissionChecker : PermissionChecker {
    var permissionGranted = true

    override suspend fun check(permissionChecker: PermissionChecker.Permission): Boolean = permissionGranted
}

fun initMockedDi(vararg modules: Module) {
    startKoin {
        modules(listOf(mockedAppModule, dataModule) + modules)
    }
}

val mockedMovie = Movie(
    0,
    "Title",
    "Overview",
    "01/01/2025",
    "",
    "",
    "EN",
    "Title",
    5.0,
    5.1,
    false
)

val defaultFakeMovies = listOf(
    mockedMovie.copy(1),
    mockedMovie.copy(2),
    mockedMovie.copy(3),
    mockedMovie.copy(4)
)
