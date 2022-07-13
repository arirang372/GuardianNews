package com.sung.guardiannews.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sung.guardiannews.model.Article
import com.sung.guardiannews.model.Field


object ArticleTypeConverters {

    @TypeConverter
    @JvmStatic
    fun stringToArticleList(data: String?): List<Article>? =
        Gson().fromJson(data, object : TypeToken<List<Article>>() {}.type)

    @TypeConverter
    @JvmStatic
    fun stringToField(data: String?): Field = Gson().fromJson(data, Field::class.java)

    @TypeConverter
    @JvmStatic
    fun articlesToString(articles: List<Article>?): String? =
        Gson().toJson(articles, object : TypeToken<List<Article>>() {}.type)

    @TypeConverter
    @JvmStatic
    fun fieldToString(field: Field?): String? = Gson().toJson(field)

}