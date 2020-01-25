package com.brunix.wondermovie.di

import android.app.Application
import androidx.room.Room
import com.brunix.data.repository.PermissionChecker
import com.brunix.data.source.LocalDataSource
import com.brunix.data.source.LocationDataSource
import com.brunix.data.source.RemoteDataSource
import com.brunix.wondermovie.R
import com.brunix.wondermovie.model.AndroidPermissionChecker
import com.brunix.wondermovie.model.PlayServicesLocationDataSource
import com.brunix.wondermovie.model.database.MovieDatabase
import com.brunix.wondermovie.model.database.RoomDataSource
import com.brunix.wondermovie.model.server.MovieDbDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun databaseProvider(app: Application) = Room.databaseBuilder(
        app,
        MovieDatabase::class.java, "movie-db"
    ).build()

    @Provides
    fun localDataSourceProvider(db: MovieDatabase): LocalDataSource = RoomDataSource(db)

    @Provides
    fun remoteDataSourceProvider(): RemoteDataSource = MovieDbDataSource()

    @Provides
    fun locationDataSourceProvider(app: Application): LocationDataSource = PlayServicesLocationDataSource(app)

    @Provides
    fun permissionCheckerProvider(app: Application): PermissionChecker = AndroidPermissionChecker(app)

    @Provides
    @Singleton
    @Named("apiKey")
    fun apiKeyProvider(app: Application): String = app.getString((R.string.api_key))
}