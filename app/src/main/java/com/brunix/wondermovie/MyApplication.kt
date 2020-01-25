package com.brunix.wondermovie

import android.app.Application
import com.brunix.wondermovie.di.DaggerWonderMovieComponent
import com.brunix.wondermovie.di.WonderMovieComponent

class MyApplication : Application() {
    lateinit var component: WonderMovieComponent
        private set

    override fun onCreate() {
        super.onCreate()
        component = DaggerWonderMovieComponent
            .factory()
            .create(this)
    }
}
