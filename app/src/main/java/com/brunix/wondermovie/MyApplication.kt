package com.brunix.wondermovie

import android.app.Application
import androidx.room.Room
import com.brunix.wondermovie.model.database.MovieDatabase

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }
}
