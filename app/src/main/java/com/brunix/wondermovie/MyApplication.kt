package com.brunix.wondermovie

import android.app.Application
import androidx.room.Room
import com.brunix.wondermovie.model.database.MovieDatabase

class MyApplication : Application() {
    lateinit var db: MovieDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            this,
            MovieDatabase::class.java, "movie-db"
        ).build()
    }
}
