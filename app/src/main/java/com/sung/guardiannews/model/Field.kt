package com.sung.guardiannews.model


data class Field(
    val headline: String = "",
    val standfirst: String = "",
    val trailText: String = "",
    val main: String = "",
    val body: String = "",
    val commentCloseDate : String = "",
    val firstPublicationDate: String = "",
    val lastModified: String = "",
    val shortUrl: String = "",
    val showInRelatedContent: Boolean,
    val thumbnail: String = "",
    val isLive: Boolean,
    val bodyText: String = ""
) : Comparable<Field> {
    override fun compareTo(other: Field) = 0
}