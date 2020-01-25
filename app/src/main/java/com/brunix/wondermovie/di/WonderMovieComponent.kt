package com.brunix.wondermovie.di

import android.app.Application
import com.brunix.wondermovie.ui.detail.MovieDetailViewModel
import com.brunix.wondermovie.ui.main.MovieListViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, InteractorModule::class, ViewModelsModule::class])
interface WonderMovieComponent {

    val movieListViewModel: MovieListViewModel
    val moviiedetailViewModel: MovieDetailViewModel

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): WonderMovieComponent
    }

}
