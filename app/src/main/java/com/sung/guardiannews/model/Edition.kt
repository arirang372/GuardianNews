package com.sung.guardiannews.model

import com.google.gson.annotations.SerializedName

data class Edition(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("webTitle") val webTitle: String,
    @field:SerializedName("webUrl") val webUrl: String,
    @field:SerializedName("apiUrl") val apiUrl: String,
    @field:SerializedName("code") val code: String
) : Comparable<Edition> {
    override fun compareTo(other: Edition) = 0
}

