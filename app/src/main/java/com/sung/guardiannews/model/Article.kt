package com.sung.guardiannews.model

import com.google.gson.annotations.SerializedName


//@Entity(
//    tableName = "content", foreignKeys = [ForeignKey(
//        entity = Section::class,
//        parentColumns = ["sectionName"],
//        childColumns = ["sectionId"], onDelete = ForeignKey.CASCADE
//    )],
//    indices = [Index(value = ["sectionId"])]
//)
data class Article(
    @field:SerializedName("primary_id")
    //@PrimaryKey(autoGenerate = true)
    //@ColumnInfo(name = "primary_id")
    val id: Int,
    @field:SerializedName("id") val resourceName: String = "",
    @field:SerializedName("type") val type: String = "",
    val sectionId: String = "",
    @field:SerializedName("sectionName") val sectionNameChild: String = "",
    val webPublicationDate: String = "",
    val webTitle: String = "",
    val webUrl: String = "",
    val apiUrl: String = "",
    val isHosted: Boolean,
    val fields: Field,
    val pillarId: String = ""
) : Comparable<Article> {
    override fun compareTo(other: Article) = 0
}