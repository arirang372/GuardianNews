package com.sung.guardiannews.data.local

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "sections", indices = [Index(value = ["sectionName"], unique = true)])
data class Section(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "primary_id")
    val id: Int,
    @ColumnInfo(name = "sectionName")
    @field:SerializedName("id") val sectionName: String,
    @ColumnInfo(name = "webTitle")
    @field:SerializedName("webTitle") val webTitle: String,
    @ColumnInfo(name = "webUrl")
    @field:SerializedName("webUrl") val webUrl: String,
    @ColumnInfo(name = "apiUrl")
    @field:SerializedName("apiUrl") val apiUrl: String,
    @Ignore
    @field:SerializedName("editions") val editions: MutableList<Edition>
) : Comparable<Section> {
    override fun compareTo(other: Section): Int {
        return 0
    }
}