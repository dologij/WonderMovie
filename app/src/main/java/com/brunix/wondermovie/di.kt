package com.brunix.wondermovie

import android.app.Application
import com.brunix.data.repository.MoviesRepository
import com.brunix.data.repository.PermissionChecker
import com.brunix.data.repository.RegionRepository
import com.brunix.data.source.LocalDataSource
import com.brunix.data.source.LocationDataSource
import com.brunix.data.source.RemoteDataSource
import com.brunix.interactor.FindMovieById
import com.brunix.interactor.GetPopularMovies
import com.brunix.interactor.ToggleMovieFavorite
import com.brunix.wondermovie.model.AndroidPermissionChecker
import com.brunix.wondermovie.model.PlayServicesLocationDataSource
import com.brunix.wondermovie.model.database.MovieDatabase
import com.brunix.wondermovie.model.database.RoomDataSource
import com.brunix.wondermovie.model.server.MovieDb
import com.brunix.wondermovie.model.server.MovieDbDataSource
import com.brunix.wondermovie.ui.detail.MovieDetailFragment
import com.brunix.wondermovie.ui.detail.MovieDetailViewModel
import com.brunix.wondermovie.ui.main.MovieListActivity
import com.brunix.wondermovie.ui.main.MovieListViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module


private val appModule = module {
    single(named("apiKey")) { androidApplication().getString(R.string.api_key)}
    single { MovieDatabase.build(get()) }
    factory<LocalDataSource> { RoomDataSource(get()) }
    factory<RemoteDataSource> { MovieDbDataSource(get()) }
    factory<LocationDataSource> { PlayServicesLocationDataSource(get()) }
    factory<PermissionChecker> { AndroidPermissionChecker(get()) }
    single<CoroutineDispatcher> { Dispatchers.Main }
    single(named("baseUrl")) { "https://api.themoviedb.org/3/" }
    single { MovieDb(get(named("baseUrl"))) }
}

val dataModule = module {
    factory { RegionRepository(get(), get()) }
    factory { MoviesRepository(get(), get(), get(), get(named("apiKey"))) }
}

private val androidModule = module {
    scope(named<MovieListActivity>()) {
        viewModel { MovieListViewModel(get(), get()) }
        scoped { GetPopularMovies(get()) }
    }
    scope(named<MovieDetailFragment>()) {
        viewModel { (id: Int) -> MovieDetailViewModel(id, get(), get(), get()) }
        scoped { FindMovieById(get()) }
        scoped { ToggleMovieFavorite(get()) }
    }
}

fun Application.initDI() {
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(listOf(appModule, dataModule, androidModule))
    }
}
