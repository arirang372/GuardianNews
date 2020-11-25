package com.sung.guardiannews.data.local

import com.google.gson.annotations.SerializedName

data class Edition(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("webTitle") val webTitle: String,
    @field:SerializedName("webUrl") val webUrl: String,
    @field:SerializedName("apiUrl") val apiUrl: String,
    @field:SerializedName("code") val code: String
)