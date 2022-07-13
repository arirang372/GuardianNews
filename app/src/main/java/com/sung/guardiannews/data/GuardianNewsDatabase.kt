package com.sung.guardiannews.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sung.guardiannews.data.local.SectionDao
import com.sung.guardiannews.model.Article
import com.sung.guardiannews.model.Section


@Database(
    entities = [Section::class, Article::class],
    version = 1,
    exportSchema = false
)
abstract class GuardianNewsDatabase : RoomDatabase() {

    companion object {
        fun create(context: Context): GuardianNewsDatabase {
            return Room.databaseBuilder(
                context,
                GuardianNewsDatabase::class.java,
                "GuardianNews.db"
            ).fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun sectionDao(): SectionDao
}