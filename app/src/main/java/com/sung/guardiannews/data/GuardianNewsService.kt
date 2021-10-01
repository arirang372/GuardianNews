package com.sung.guardiannews.data

import android.util.Log
import com.sung.guardiannews.BuildConfig
import com.sung.guardiannews.data.remote.GuardianServiceResponse
import com.sung.guardiannews.model.Article
import com.sung.guardiannews.model.Section
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface GuardianNewsService {

    @GET("/sections")
    suspend fun getSections(
        @Query(value = "api-key") apiKey: String = BuildConfig.GUARDIAN_API_KEY
    ): GuardianServiceResponse<Section>

    @GET("/{sectionId}")
    suspend fun getArticles(
        @Path("sectionId") sectionId: String,
        @Query("page") page: Int = 1,
        @Query("page-size") pageSize: Int = 10,
        @Query("type") type: String = "article", //type = "article or liveblog"
        @Query("show-fields") showFields: String = "all",
        @Query("show-elements") showElements: String = "all",
        @Query("show-most-viewed") showMostViewed: Boolean = true,
        @Query(value = "api-key") apiKey: String = BuildConfig.GUARDIAN_API_KEY
    ): GuardianServiceResponse<Article>

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