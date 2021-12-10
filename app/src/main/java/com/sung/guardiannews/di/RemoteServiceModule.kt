package com.sung.guardiannews.di

import com.sung.guardiannews.data.GuardianNewsService
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
}