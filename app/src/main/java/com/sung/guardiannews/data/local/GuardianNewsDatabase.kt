//package com.sung.guardiannews.data.local
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import com.sung.guardiannews.model.Article
//import com.sung.guardiannews.model.Section
//
//@Database(entities = [Section::class, Article::class], version = 1, exportSchema = false)
//abstract class GuardianNewsDatabase : RoomDatabase() {
//    companion object {
//        @Volatile
//        private var INSTANCE: GuardianNewsDatabase? = null
//
//        fun create(context: Context): GuardianNewsDatabase {
//            val temporaryInstance = INSTANCE
//
//            if (temporaryInstance != null)
//                return temporaryInstance
//
//            synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context,
//                    GuardianNewsDatabase::class.java,
//                    "GuardianNews.db"
//                )
//                    .fallbackToDestructiveMigration()
//                    .build()
//                INSTANCE = instance
//                return instance
//            }
//
//        }
//    }
//}