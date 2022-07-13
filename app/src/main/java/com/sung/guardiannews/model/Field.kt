package com.sung.guardiannews.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sung.guardiannews.extensions.formatTo
import com.sung.guardiannews.extensions.toDate
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "fields")
@Parcelize
data class Field(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val headline: String? = "",
    val standfirst: String? = "",
    val trailText: String? = "",
    val main: String? = "",
    val body: String? = "",
    val commentCloseDate: String? = "",
    val firstPublicationDate: String? = "",
    val lastModified: String? = "",
    val shortUrl: String? = "",
    val showInRelatedContent: Boolean,
    val thumbnail: String? = "",
    val isLive: Boolean,
    val bodyText: String? = ""
) : Comparable<Field>, Parcelable {
    override fun compareTo(other: Field) = 0
    fun lastModifiedFormatted() = lastModified?.toDate()?.formatTo("MMM dd, yyyy hh:mm a")
}