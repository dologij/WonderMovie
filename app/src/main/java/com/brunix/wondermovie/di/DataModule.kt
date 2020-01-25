package com.brunix.wondermovie.di

import com.brunix.data.repository.MoviesRepository
import com.brunix.data.repository.PermissionChecker
import com.brunix.data.repository.RegionRepository
import com.brunix.data.source.LocalDataSource
import com.brunix.data.source.LocationDataSource
import com.brunix.data.source.RemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class DataModule {

    @Provides
    fun regionRepositoryProvider(
        locationDataSource: LocationDataSource,
        permissionChecker: PermissionChecker
    ) = RegionRepository(locationDataSource, permissionChecker)

    @Provides
    fun moviesRepositoryProvider(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
        regionRepository: RegionRepository,
        @Named("apiKey")apiKey: String
    ) = MoviesRepository(localDataSource, remoteDataSource, regionRepository, apiKey)
}