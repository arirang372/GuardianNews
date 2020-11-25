package com.sung.guardiannews.data.local

import androidx.room.*
import com.google.gson.annotations.SerializedName


@Entity(
    tableName = "contents", foreignKeys = [ForeignKey(
        entity = Section::class,
        parentColumns = ["sectionName"],
        childColumns = ["sectionId"], onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["sectionId"])]
)
data class Content(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "primary_id")
    val id: Int,
    @field:SerializedName("id") val resourceName: String,
    @field:SerializedName("type") val type: String,
    @field:SerializedName("sectionId") val sectionId: String,
    @field:SerializedName("sectionName") val sectionNameChild: String,
    @field:SerializedName("webPublicationDate") val webPublicationDate: String,
    @field:SerializedName("webTitle") val webTitle: String,
    @field:SerializedName("webUrl") val webUrl: String,
    @field:SerializedName("apiUrl") val apiUrl: String,
    @field:SerializedName("isHosted") val isHosted: Boolean,

    val isRead: Boolean
) : Comparable<Content> {
    override fun compareTo(other: Content): Int {
        return 0
    }
}