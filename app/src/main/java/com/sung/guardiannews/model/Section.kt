package com.sung.guardiannews.model

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

//@Entity(tableName = "sections", indices = [Index(value = ["sectionName"], unique = true)])
data class Section(
    //@PrimaryKey(autoGenerate = true)
    @SerializedName("primary_id")
    @ColumnInfo(name = "primary_id")
    val id: Int,
    @SerializedName("id") val sectionName: String = "",
    val webTitle: String = "",
    val webUrl: String = "",
    val apiUrl: String = "",
    var articles: List<Article> = mutableListOf()
) : Comparable<Section> {
    override fun compareTo(other: Section) = 0
}