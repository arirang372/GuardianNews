package com.sung.guardiannews.model

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
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
    @field:SerializedName("id") val resourceName: String? = "",
    @field:SerializedName("type") val type: String? = "",
    val sectionId: String? = "",
    @field:SerializedName("sectionName") val sectionNameChild: String? = "",
    val webPublicationDate: String? = "",
    val webTitle: String? = "",
    val webUrl: String? = "",
    val apiUrl: String? = "",
    val isHosted: Boolean,
    val fields: Field?,
    val pillarId: String? = "",
    val mostViewed: MutableList<Article>? = mutableListOf()
) : Comparable<Article>, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readParcelable<Field>(Field::class.java.classLoader),
        parcel.readString(),
        arrayListOf<Article>().apply {
            parcel.readArrayList(Article::class.java.classLoader)
        }
    )

    override fun compareTo(other: Article) = 0
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(resourceName)
        parcel.writeString(type)
        parcel.writeString(sectionId)
        parcel.writeString(sectionNameChild)
        parcel.writeString(webPublicationDate)
        parcel.writeString(webTitle)
        parcel.writeString(webUrl)
        parcel.writeString(apiUrl)
        parcel.writeByte(if (isHosted) 1 else 0)
        parcel.writeParcelable(fields, flags)
        parcel.writeString(pillarId)
        if (Build.VERSION.SDK_INT >= 29) {
            parcel.writeParcelableList(mostViewed, flags)
        } else {
            parcel.writeList(mostViewed as List<Article>)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Article> {
        override fun createFromParcel(parcel: Parcel) = Article(parcel)
        override fun newArray(size: Int): Array<Article?> {
            return arrayOfNulls(size)
        }
    }
}