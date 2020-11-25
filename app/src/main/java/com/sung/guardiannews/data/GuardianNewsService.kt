package com.sung.guardiannews.data

import android.util.Log
import com.sung.guardiannews.data.remote.GuardianServiceResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface GuardianNewsService {

    @GET("/sections")
    suspend fun <T : Comparable<T>> getSections(
        @Query(value = "sections") sectionId: String,
        @Query(value = "api-key") apiKey: String
    ) : GuardianServiceResponse<T>

    companion object {
        private const val BASE_URL = "https://content.guardianapis.com"
        fun create(): GuardianNewsService {
            val logger = HttpLoggingInterceptor { Log.d("API", it) }.apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            return Retrofit.Builder().baseUrl(BASE_URL)
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(logger)
                        .build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GuardianNewsService::class.java)

        }
    }
}