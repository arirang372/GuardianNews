package com.sung.guardiannews.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

//@Entity(tableName = "sections", indices = [Index(value = ["sectionName"], unique = true)])
@Parcelize
data class Section(
    //@PrimaryKey(autoGenerate = true)
    @SerializedName("primary_id")
    @ColumnInfo(name = "primary_id")
    val id: Int,
    @SerializedName("id") val sectionName: String? = "",
    val webTitle: String? = "",
    val webUrl: String? = "",
    val apiUrl: String? = "",
    var articles: List<Article>? = mutableListOf()
) : Comparable<Section>, Parcelable {
    override fun compareTo(other: Section) = 0

    @ExperimentalStdlibApi
    fun titleCapitalize(input: String): String {
        val words: List<String> = input.split("-")
        var newWord = ""
        for (single in words) {
            var newInput = single.substring(0, 1).uppercase() + single.substring(1).lowercase()
            newWord += "$newInput "
        }
        return newWord
    }
}