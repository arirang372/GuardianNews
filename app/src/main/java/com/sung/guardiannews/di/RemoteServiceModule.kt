package com.sung.guardiannews.di

import com.sung.guardiannews.data.remote.GuardianNewsApiContract
import com.sung.guardiannews.data.remote.GuardianNewsApiHelper
import com.sung.guardiannews.data.remote.GuardianNewsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteServiceModule {

    @Singleton
    @Provides
    fun providesGuardianNewsService(): GuardianNewsService {
        return GuardianNewsService.create()
    }

    @Singleton
    @Provides
    fun providesGuardianNewsApiContract(service: GuardianNewsService) : GuardianNewsApiContract=
        GuardianNewsApiHelper(service)
}