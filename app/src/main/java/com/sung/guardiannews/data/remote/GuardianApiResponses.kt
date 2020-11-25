package com.sung.guardiannews.data.remote

import com.google.gson.annotations.SerializedName
import com.sung.guardiannews.data.local.Edition

class GuardianServiceResponse<T : Comparable<T>>(
    @field:SerializedName("response") val response: GuardianNewsResponse<T>
)

class GuardianNewsResponse<T : Comparable<T>>(
    @field:SerializedName("status") val status: String,
    @field:SerializedName("total") val total: Int,
    @field:SerializedName("startIndex") val startIndex: Int,
    @field:SerializedName("pageSize") val pageSize: Int,
    @field:SerializedName("currentPage") val currentPage: Int,
    @field:SerializedName("pages") val pages: Int,
    @field:SerializedName("edition") val edition: Edition,
    @field:SerializedName("results") val results: List<T>
)


