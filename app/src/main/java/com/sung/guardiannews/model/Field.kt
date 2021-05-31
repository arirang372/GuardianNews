package com.sung.guardiannews.model

import com.sung.guardiannews.extensions.formatTo
import com.sung.guardiannews.extensions.toDate


data class Field(
    val headline: String = "",
    val standfirst: String = "",
    val trailText: String = "",
    val main: String = "",
    val body: String = "",
    val commentCloseDate: String = "",
    val firstPublicationDate: String = "",
    val lastModified: String = "",
    val shortUrl: String = "",
    val showInRelatedContent: Boolean,
    val thumbnail: String = "",
    val isLive: Boolean,
    val bodyText: String = ""
) : Comparable<Field> {
    override fun compareTo(other: Field) = 0
    fun lastModifiedFormatted() : String {
        return lastModified.toDate().formatTo("MMM dd, yyyy hh:mm a")
    }
}