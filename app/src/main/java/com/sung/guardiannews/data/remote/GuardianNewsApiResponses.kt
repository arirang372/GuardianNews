package com.sung.guardiannews.data.remote

import com.google.gson.annotations.SerializedName
import com.sung.guardiannews.model.Article
import com.sung.guardiannews.model.Edition

class GuardianServiceResponse<out T>(
    @SerializedName("response") val response: GuardianNewsResponse<T>,
)

class GuardianNewsResponse<out T>(
    @SerializedName("status") val status: String = "",
    @SerializedName("total") val total: Int = 0,
    @SerializedName("startIndex") val startIndex: Int = 0,
    @SerializedName("pageSize") val pageSize: Int = 0,
    @SerializedName("currentPage") val currentPage: Int = 0,
    @SerializedName("pages") val pages: Int = 0,
    @SerializedName("edition") val edition: Edition,
    @SerializedName("results") val results: List<T>,
    val mostViewed: List<Article>
)

class GuardianServiceResponseResult<out T>(
    val status: Status,
    val data: T?,
    val message: String = ""
) {

    companion object {
        fun <T> success(data: T?): GuardianServiceResponseResult<T> {
            return GuardianServiceResponseResult(Status.SUCCESS, data, "")
        }

        fun <T> error(message: String, data: T?): GuardianServiceResponseResult<T> {
            return GuardianServiceResponseResult(Status.ERROR, data, message)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR
}
