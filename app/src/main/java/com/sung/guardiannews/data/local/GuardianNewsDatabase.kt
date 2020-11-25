package com.sung.guardiannews.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase


abstract class GuardianNewsDatabase : RoomDatabase() {
    companion object {
        fun create(context: Context): GuardianNewsDatabase {
            return Room.databaseBuilder(
                context,
                GuardianNewsDatabase::class.java,
                "GuardianNews.db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}