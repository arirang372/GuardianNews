package com.sung.guardiannews.di

import android.content.Context
import com.sung.guardiannews.data.local.GuardianNewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providesAppDatabase(@ApplicationContext context: Context) =
        GuardianNewsDatabase.create(context)

    @Provides
    fun provideSectionDao(database: GuardianNewsDatabase) = database.sectionDao()
}