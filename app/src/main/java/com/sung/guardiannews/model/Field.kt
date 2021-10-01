package com.sung.guardiannews.model

import android.os.Parcel
import android.os.Parcelable
import com.sung.guardiannews.extensions.formatTo
import com.sung.guardiannews.extensions.toDate


data class Field(
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
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString()
    ) {
    }

    override fun compareTo(other: Field) = 0
    fun lastModifiedFormatted() = lastModified?.toDate()?.formatTo("MMM dd, yyyy hh:mm a")
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(headline)
        parcel.writeString(standfirst)
        parcel.writeString(trailText)
        parcel.writeString(main)
        parcel.writeString(body)
        parcel.writeString(commentCloseDate)
        parcel.writeString(firstPublicationDate)
        parcel.writeString(lastModified)
        parcel.writeString(shortUrl)
        parcel.writeByte(if (showInRelatedContent) 1 else 0)
        parcel.writeString(thumbnail)
        parcel.writeByte(if (isLive) 1 else 0)
        parcel.writeString(bodyText)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Field> {
        override fun createFromParcel(parcel: Parcel): Field {
            return Field(parcel)
        }

        override fun newArray(size: Int): Array<Field?> {
            return arrayOfNulls(size)
        }
    }
}