package com.sung.guardiannews.data.local

import androidx.room.*
import com.sung.guardiannews.model.Section

@Dao
interface SectionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(sections: List<Section>)

    @Query("SELECT * FROM sections")
    suspend fun getAllSections(): List<Section>

    @Update
    suspend fun updateSection(section: Section)

    @Query("SELECT * FROM sections WHERE sectionName = :sectionName AND articleType = :articleType")
    suspend fun getSectionsBy(sectionName: String, articleType: String): List<Section>

    @Delete
    suspend fun deleteAll(sections: List<Section>)
}