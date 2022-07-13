package com.sung.guardiannews.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sung.guardiannews.model.Article
import com.sung.guardiannews.model.Field
import com.sung.guardiannews.model.Section

@Database(
    entities = [Section::class, Article::class, Field::class],
    version = 1,
    exportSchema = false
)
abstract class GuardianNewsDatabase : RoomDatabase() {
    abstract fun sectionDao(): SectionDao

    companion object {
        @Volatile
        private var instance: GuardianNewsDatabase? = null

        fun create(context: Context): GuardianNewsDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context): GuardianNewsDatabase {
            return Room.databaseBuilder(
                context,
                GuardianNewsDatabase::class.java,
                "GuardianNews.db"
            )
                .build()
        }
    }
}