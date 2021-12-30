package com.sung.guardiannews.view.adapters

import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import com.sung.guardiannews.R
import com.sung.guardiannews.databinding.MostViewedArticleCardBinding
import com.sung.guardiannews.databinding.NewsSectionArticleItemBinding
import com.sung.guardiannews.model.Article
import com.sung.guardiannews.model.Field
import com.sung.guardiannews.view.GuardianArticleCallback
import com.sung.guardiannews.view.GuardianNewsCallback

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("android:visibility")
    fun setVisibility(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

//    @JvmStatic
//    @BindingAdapter(value = ["sections", "callback"])
//    fun setSections(view: RecyclerView, sections : List<Section>?, callback: GuardianNewsCallback){
//        val adapter = sections?.let {
//            GuardianGenericRecyclerViewAdapter<NewsSectionArticlesBinding, Section>(
//                R.layout.news_section_articles,
//                it,
//                callback
//            )
//        }
//        view.adapter = adapter
//    }

    @JvmStatic
    @BindingAdapter(value = ["articles", "callback"])
    fun setArticles(view: RecyclerView, articles: List<Article>?, callback: GuardianNewsCallback) {
        if (articles != null) {
            view.adapter =
                GuardianArticleListAdapter(R.layout.news_section_article_item, callback).apply {
                    submitList(articles)
                }
//            GuardianGenericRecyclerViewAdapter<NewsSectionArticleItemBinding, Article>(
//                R.layout.news_section_article_item,
//                articles,
//                callback
//            ).also {
//                view.adapter = it
//            }
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["mostViewed", "callback"])
    fun setMostViewed(
        view: RecyclerView, articles: List<Article>?, callback: GuardianArticleCallback
    ) {
        if (articles != null) {
            view.adapter =
                GuardianArticleListAdapter(R.layout.most_viewed_article_item, callback).apply {
                    submitList(articles)
                }

//            GuardianGenericRecyclerViewAdapter<MostViewedArticleCardBinding, Article>(
//                R.layout.most_viewed_article_item,
//                articles,
//                callback
//            ).also {
//                view.adapter = it
//            }
        }
    }

    @JvmStatic
    @BindingAdapter("android:src")
    fun setImageResource(imageView: ImageView, fields: Field) {
        if (fields.thumbnail.isNullOrEmpty()) {
            Picasso.get().cancelRequest(imageView)
            imageView.setImageDrawable(null)
            return
        }
        Picasso.get().load(fields.thumbnail).into(imageView)
    }

    @JvmStatic
    @BindingAdapter("app:srcCompat")
    fun setImageResourceCompat(imageView: ShapeableImageView, fields: Field) {
        if (fields.thumbnail.isNullOrEmpty()) {
            Picasso.get().cancelRequest(imageView)
            imageView.setImageDrawable(null)
            return
        }
        Picasso.get().load(fields.thumbnail).into(imageView)
    }

    @JvmStatic
    @BindingAdapter("android:text")
    fun setWebviewText(webview: WebView, fields: Field) {
        fields.body?.let {
            webview.loadData(
                it,
                "text/html; charset=utf-8",
                "UTF-8"
            )
        }
    }
}