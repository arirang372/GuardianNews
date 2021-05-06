//package com.sung.guardiannews.data.local
//
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import com.sung.guardiannews.model.Section
//
//
//@Dao
//interface SectionDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAll(posts: List<Section>)
//
//    @Query("SELECT * FROM sections")
//    suspend fun getAllSections(): List<Section>
//}