package com.sung.guardiannews.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

//@Entity(tableName = "sections", indices = [Index(value = ["sectionName"], unique = true)])
data class Section(
    //@PrimaryKey(autoGenerate = true)
    @SerializedName("primary_id")
    @ColumnInfo(name = "primary_id")
    val id: Int,
    @SerializedName("id") val sectionName: String? = "",
    val webTitle: String? = "",
    val webUrl: String? = "",
    val apiUrl: String? = "",
    var articles: List<Article> = mutableListOf()
) : Comparable<Section>, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
    )

    override fun compareTo(other: Section) = 0
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(sectionName)
        parcel.writeString(webTitle)
        parcel.writeString(webUrl)
        parcel.writeString(apiUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Section> {
        override fun createFromParcel(parcel: Parcel): Section {
            return Section(parcel)
        }

        override fun newArray(size: Int): Array<Section?> {
            return arrayOfNulls(size)
        }
    }
}