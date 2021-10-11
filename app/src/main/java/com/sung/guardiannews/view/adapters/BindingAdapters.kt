package com.sung.guardiannews.view.adapters

import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import com.sung.guardiannews.model.Article
import com.sung.guardiannews.model.Field

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("android:visibility")
    fun setVisibility(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("articles")
    fun setArticles(view: RecyclerView, articles: List<Article>?) {
        if (articles != null) {
            view.adapter = GuardianArticleListAdapter().apply {
                submitList(articles)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("mostViewed")
    fun setMostViewed(view: RecyclerView, articles: List<Article>?) {
        if (articles != null) {
            view.adapter = GuardianMostViewedArticleListAdapter().apply {
                submitList(articles)
            }
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