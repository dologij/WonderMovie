package com.brunix.wondermovie.di

import android.app.Application
import com.brunix.wondermovie.ui.detail.MovieDetailActivityComponent
import com.brunix.wondermovie.ui.detail.MovieDetailActivityModule
import com.brunix.wondermovie.ui.main.MovieListActivityComponent
import com.brunix.wondermovie.ui.main.MovieListActivityModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class])
interface WonderMovieComponent {

    fun plus(module: MovieListActivityModule): MovieListActivityComponent
    fun plus(module: MovieDetailActivityModule): MovieDetailActivityComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): WonderMovieComponent
    }

}
